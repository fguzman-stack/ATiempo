"""Capture only the preview app; never export existing user app data."""
import argparse
import io
import subprocess
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
sys.stdout.reconfigure(encoding="utf-8")
PACKAGE = "com.atiempo.app.preview"

def adb(*args):
    return subprocess.check_output(["adb", *args])

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("action", choices=["dump", "capture", "installer"])
    parser.add_argument("--name", default="preview")
    args = parser.parse_args()
    windows = adb("shell", "dumpsys", "window").decode(errors="replace")
    focused = [line for line in windows.splitlines() if "mCurrentFocus" in line]
    allowed = "packageinstaller" if args.action == "installer" else PACKAGE
    if not any(allowed in line for line in focused):
        raise SystemExit("Preview app must be in the foreground before inspecting or capturing.")
    if args.action == "dump":
        subprocess.run(["adb", "shell", "uiautomator", "dump", "/data/local/tmp/atiempo-ui.xml"],
                       check=True, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        xml = adb("shell", "cat", "/data/local/tmp/atiempo-ui.xml")
        for node in ET.fromstring(xml).iter("node"):
            text = node.get("text") or node.get("content-desc")
            if text:
                print(text, node.get("bounds"))
        return
    from PIL import Image
    image = Image.open(io.BytesIO(adb("exec-out", "screencap", "-p")))
    # Remove system status/navigation areas; keep the real app content.
    image = image.crop((0, int(image.height * .035), image.width, int(image.height * .94)))
    output = ROOT / "build" if args.action == "installer" else ROOT / "docs" / "screenshots"
    output.mkdir(parents=True, exist_ok=True)
    name = Path(args.name).name
    image.save(output / f"{name}.png")
    print(f"Saved docs/screenshots/{name}.png")

if __name__ == "__main__":
    main()
