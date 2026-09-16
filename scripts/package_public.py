"""Build a clean, history-free source archive and preview distribution."""
import hashlib
import re
import shutil
import sys
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ROOT_FILES = {
    ".gitignore", ".env.example", "README.md", "LICENSE", "PRIVACY.md",
    "CONTRIBUTING.md", "CHANGELOG.md", "THIRD_PARTY_NOTICES.md",
    "build.gradle.kts", "settings.gradle.kts", "gradle.properties", "gradlew", "gradlew.bat",
}
TREES = ("app/src", "gradle", "docs", "scripts", ".github")
APP_FILES = ("app/build.gradle.kts", "app/proguard-rules.pro", "app/.gitignore")
DENIED_PARTS = {".git", ".idea", "build", ".gradle", "__pycache__"}
DENIED_SUFFIXES = {".jks", ".keystore", ".p12", ".pem", ".apk", ".aab", ".mp3"}
PATTERNS = (
    rb"AIza[0-9A-Za-z_-]{35}",
    rb"ca-app-pub-[0-9]{16}[~/][0-9]{10}",
    rb"-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----",
    rb"gh[pousr]_[A-Za-z0-9]{30,}",
    rb"[A-Za-z]:[/\\]Users[/\\][^\s/\\]+",
)

def sources():
    files = {ROOT / name for name in ROOT_FILES | set(APP_FILES)}
    for tree in TREES:
        files.update(p for p in (ROOT / tree).rglob("*") if p.is_file())
    return sorted(p for p in files if p.is_file() and not p.is_symlink()
                  and not (set(p.relative_to(ROOT).parts) & DENIED_PARTS)
                  and p.suffix.lower() not in DENIED_SUFFIXES
                  and p.name not in {"google-services.json", "local.properties", ".env"})

def main():
    files = sources()
    for path in files:
        data = path.read_bytes()
        if any(re.search(pattern, data) for pattern in PATTERNS):
            raise SystemExit(f"Potential private identifier in {path.relative_to(ROOT)}; packaging stopped.")
    if "--check" in sys.argv:
        print(f"Public source scan passed: {len(files)} files.")
        return
    apk = ROOT / "app/build/outputs/apk/debug/app-debug.apk"
    if not apk.is_file():
        raise SystemExit("Build :app:assembleDebug first.")
    output = ROOT / "dist"
    output.mkdir(exist_ok=True)
    archive = output / "a-tiempo-source.zip"
    with zipfile.ZipFile(archive, "w", zipfile.ZIP_DEFLATED) as zipped:
        for path in files:
            name = path.relative_to(ROOT).as_posix()
            info = zipfile.ZipInfo(name)
            info.compress_type = zipfile.ZIP_DEFLATED
            info.external_attr = (0o100755 if name == "gradlew" else 0o100644) << 16
            zipped.writestr(info, path.read_bytes())
    target = output / "a-tiempo-1.0-preview.apk"
    shutil.copyfile(apk, target)
    checksums = "".join(f"{hashlib.sha256(p.read_bytes()).hexdigest()}  {p.name}\n"
                        for p in (target, archive))
    (output / "SHA256SUMS.txt").write_text(checksums, encoding="utf-8")
    print(f"Packaged {len(files)} source files. Distribution: dist/")
    print(checksums)

if __name__ == "__main__":
    main()
