package com.example.ui.translation

import java.util.Calendar
import java.util.Locale

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    ES("es", "Español", "🇪🇸"),
    EN("en", "English", "🇬🇧"),
    HI("hi", "हिन्दी", "🇮🇳"),
    ZH("zh", "简体中文", "🇨🇳"),
    AR("ar", "العربية", "🇸🇦"),
    PT("pt", "Português", "🇧🇷"),
    FR("fr", "Français", "🇫🇷")
}

object Translations {
    private val strings = mapOf(
        "app_name" to mapOf(
            AppLanguage.ES to "A Tiempo",
            AppLanguage.EN to "A Tiempo",
            AppLanguage.HI to "A Tiempo",
            AppLanguage.ZH to "A Tiempo",
            AppLanguage.AR to "A Tiempo",
            AppLanguage.PT to "A Tiempo",
            AppLanguage.FR to "A Tiempo"
        ),
        "tagline" to mapOf(
            AppLanguage.ES to "Recordatorios con intención.",
            AppLanguage.EN to "Reminders with intention.",
            AppLanguage.HI to "इरादे के साथ अनुस्मारक।",
            AppLanguage.ZH to "带有意图的提醒。",
            AppLanguage.AR to "تذكيرات ذات مغزى وأثر.",
            AppLanguage.PT to "Lembretes com intenção.",
            AppLanguage.FR to "Rappels avec intention."
        ),
        "select_lang" to mapOf(
            AppLanguage.ES to "Elige tu idioma",
            AppLanguage.EN to "Select your language",
            AppLanguage.HI to "अपनी भाषा चुनें",
            AppLanguage.ZH to "选择你的语言",
            AppLanguage.AR to "اختر لغتك",
            AppLanguage.PT to "Escolha seu idioma",
            AppLanguage.FR to "Choisissez votre langue"
        ),
        "continue" to mapOf(
            AppLanguage.ES to "Continuar",
            AppLanguage.EN to "Continue",
            AppLanguage.HI to "जारी रखें",
            AppLanguage.ZH to "继续",
            AppLanguage.AR to "استمرار",
            AppLanguage.PT to "Continuar",
            AppLanguage.FR to "Continuer"
        ),
        "onboarding_title" to mapOf(
            AppLanguage.ES to "Bienvenido a A Tiempo",
            AppLanguage.EN to "Welcome to A Tiempo",
            AppLanguage.HI to "एतिएम्पो में आपका स्वागत है",
            AppLanguage.ZH to "欢迎来到 A Tiempo",
            AppLanguage.AR to "مرحبًا بك في أ تيمبو",
            AppLanguage.PT to "Bem-vindo ao A Tiempo",
            AppLanguage.FR to "Bienvenue sur A Tiempo"
        ),
        "onboarding_desc" to mapOf(
            AppLanguage.ES to "Construye hábitos consistentes a través de rachas, recompensas progresivas y calma visual.",
            AppLanguage.EN to "Build consistent habits through streaks, progressive rewards, and visual calm.",
            AppLanguage.HI to "लगातार बने रहने वाले सिलसिले, प्रगतिशील पुरस्कार और दृश्य शांति के माध्यम से आदतें बनाएं।",
            AppLanguage.ZH to "通过连续记录、渐进式奖励和视觉平静，培养持久的习惯。",
            AppLanguage.AR to "ابنِ عادات مستمرة من خلال السلاسل، والمكافآت التصاعدية، والهدوء البصري.",
            AppLanguage.PT to "Construa hábitos consistentes através de sequências, recompensas progressivas e calma visual.",
            AppLanguage.FR to "Construisez des habitudes régulières grâce aux séquences, aux récompenses progressives et au calme visuel."
        ),
        "disclaimer_title" to mapOf(
            AppLanguage.ES to "Compromiso Ético y Privacidad",
            AppLanguage.EN to "Ethical Commitment & Privacy",
            AppLanguage.HI to "नैतिक प्रतिबद्धता और गोपनीयता",
            AppLanguage.ZH to "伦理承诺与隐私",
            AppLanguage.AR to "الالتزام الأخلاقي والخصوصية",
            AppLanguage.PT to "Compromisso Ético e Privacidade",
            AppLanguage.FR to "Engagement Éthique et Confidentialité"
        ),
        "disclaimer_desc" to mapOf(
            AppLanguage.ES to "Esta aplicación funciona sin conexión a internet. Toda tu información se queda en tu teléfono y nadie más puede verla. No rastreamos tu actividad ni compartimos tus datos. No proporcionamos consejo médico.",
            AppLanguage.EN to "This app works without an internet connection. All your information stays on your phone and no one else can see it. We don't track your activity or share your data. We do not provide medical advice.",
            AppLanguage.HI to "यह ऐप बिना इंटरनेट कनेक्शन के काम करता है। आपकी सारी जानकारी आपके फ़ोन पर रहती है और कोई और इसे नहीं देख सकता। हम आपकी गतिविधि को ट्रैक नहीं करते या आपका डेटा साझा नहीं करते। हम चिकित्सा सलाह नहीं देते।",
            AppLanguage.ZH to "此应用无需网络连接即可使用。您的所有信息都保存在您的手机上，其他人无法查看。我们不跟踪您的活动，也不分享您的数据。我们不提供医疗建议。",
            AppLanguage.AR to "يعمل هذا التطبيق دون الحاجة إلى اتصال بالإنترنت. جميع معلوماتك تبقى على هاتفك ولا يمكن لأي شخص آخر رؤيتها. نحن لا نتتبع نشاطك ولا نشارك بياناتك. نحن لا نقدم مشورة طبية.",
            AppLanguage.PT to "Este aplicativo funciona sem conexão com a internet. Todas as suas informações ficam no seu telefone e ninguém mais pode vê-las. Não rastreamos sua atividade nem compartilhamos seus dados. Não fornecemos aconselhamento médico.",
            AppLanguage.FR to "Cette application fonctionne sans connexion Internet. Toutes vos informations restent sur votre téléphone et personne d'autre ne peut les voir. Nous ne suivons pas votre activité ni ne partageons vos données. Nous ne fournissons pas de conseil médical."
        ),
        "start_calm" to mapOf(
            AppLanguage.ES to "Empezar con calma",
            AppLanguage.EN to "Start with Calm",
            AppLanguage.HI to "शांति के साथ शुरू करें",
            AppLanguage.ZH to "静心开始",
            AppLanguage.AR to "ابدأ بهدوء",
            AppLanguage.PT to "Começar com calma",
            AppLanguage.FR to "Commencer en douceur"
        ),
        "filter_all" to mapOf(
            AppLanguage.ES to "Todos",
            AppLanguage.EN to "All",
            AppLanguage.HI to "सभी",
            AppLanguage.ZH to "全部",
            AppLanguage.AR to "الكل",
            AppLanguage.PT to "Todos",
            AppLanguage.FR to "Tous"
        ),
        "filter_today" to mapOf(
            AppLanguage.ES to "Hoy",
            AppLanguage.EN to "Today",
            AppLanguage.HI to "आज",
            AppLanguage.ZH to "今天",
            AppLanguage.AR to "اليوم",
            AppLanguage.PT to "Hoje",
            AppLanguage.FR to "Aujourd'hui"
        ),
        "filter_week" to mapOf(
            AppLanguage.ES to "Esta semana",
            AppLanguage.EN to "This week",
            AppLanguage.HI to "इस सप्ताह",
            AppLanguage.ZH to "本周",
            AppLanguage.AR to "هذا الأسبوع",
            AppLanguage.PT to "Esta semana",
            AppLanguage.FR to "Cette semaine"
        ),
        "sort_time" to mapOf(
            AppLanguage.ES to "Hora",
            AppLanguage.EN to "Time",
            AppLanguage.HI to "समय",
            AppLanguage.ZH to "时间",
            AppLanguage.AR to "الوقت",
            AppLanguage.PT to "Horário",
            AppLanguage.FR to "Heure"
        ),
        "sort_name" to mapOf(
            AppLanguage.ES to "Nombre",
            AppLanguage.EN to "Name",
            AppLanguage.HI to "नाम",
            AppLanguage.ZH to "名称",
            AppLanguage.AR to "الاسم",
            AppLanguage.PT to "Nome",
            AppLanguage.FR to "Nom"
        ),
        "sort_pending" to mapOf(
            AppLanguage.ES to "Pendientes",
            AppLanguage.EN to "Pending",
            AppLanguage.HI to "लंबित",
            AppLanguage.ZH to "待办",
            AppLanguage.AR to "المعلقة",
            AppLanguage.PT to "Pendentes",
            AppLanguage.FR to "En attente"
        ),
        "next_reminder" to mapOf(
            AppLanguage.ES to "Próxima Intención",
            AppLanguage.EN to "Next Intention",
            AppLanguage.HI to "अगला इरादा",
            AppLanguage.ZH to "下一个意图",
            AppLanguage.AR to "النية القادمة",
            AppLanguage.PT to "Próxima Intenção",
            AppLanguage.FR to "Prochaine Intention"
        ),
        "no_reminders" to mapOf(
            AppLanguage.ES to "No hay recordatorios creados aún.",
            AppLanguage.EN to "No reminders created yet.",
            AppLanguage.HI to "अभी तक कोई अनुस्मारक नहीं बनाया गया है।",
            AppLanguage.ZH to "尚无创建的的提醒。",
            AppLanguage.AR to "لم يتم إنشاء تذكيرات بعد.",
            AppLanguage.PT to "Nenhum lembrete criado ainda.",
            AppLanguage.FR to "Aucun rappel créé pour l'instant."
        ),
        "no_reminders_day" to mapOf(
            AppLanguage.ES to "Sin recordatorios para este día.",
            AppLanguage.EN to "No reminders for this day.",
            AppLanguage.HI to "इस दिन के लिए कोई अनुस्मारक नहीं।",
            AppLanguage.ZH to "当天没有提醒。",
            AppLanguage.AR to "لا تذكيرات لهذا اليوم.",
            AppLanguage.PT to "Nenhum lembrete para este dia.",
            AppLanguage.FR to "Aucun rappel pour ce jour."
        ),
        "add_quick" to mapOf(
            AppLanguage.ES to "Intención Rápida",
            AppLanguage.EN to "Quick Intention",
            AppLanguage.HI to "त्वरित इरादा",
            AppLanguage.ZH to "快速意图",
            AppLanguage.AR to "نية سريعة",
            AppLanguage.PT to "Intenção Rápida",
            AppLanguage.FR to "Intention Rapide"
        ),
        "add_rem" to mapOf(
            AppLanguage.ES to "Añadir Recordatorio",
            AppLanguage.EN to "Add Reminder",
            AppLanguage.HI to "अनुस्मारक जोड़ें",
            AppLanguage.ZH to "添加提醒",
            AppLanguage.AR to "إضافة تذكير",
            AppLanguage.PT to "Adicionar Lembrete",
            AppLanguage.FR to "Ajouter un Rappel"
        ),
        "edit_rem" to mapOf(
            AppLanguage.ES to "Editar Recordatorio",
            AppLanguage.EN to "Edit Reminder",
            AppLanguage.HI to "अनुस्मारक संपादित करें",
            AppLanguage.ZH to "编辑提醒",
            AppLanguage.AR to "تعديل التذكير",
            AppLanguage.PT to "Editar Lembrete",
            AppLanguage.FR to "Modifier le Rappel"
        ),
        "name_hint" to mapOf(
            AppLanguage.ES to "Nombre del recordatorio (máx. 40)",
            AppLanguage.EN to "Reminder name (max 40)",
            AppLanguage.HI to "अनुस्मारक का नाम (अधिकतम 40)",
            AppLanguage.ZH to "提醒名称（最多40字）",
            AppLanguage.AR to "اسم التذكير (الحد الأقصى 40)",
            AppLanguage.PT to "Nome do lembrete (máx. 40)",
            AppLanguage.FR to "Nom du rappel (max 40)"
        ),
        "notes_hint" to mapOf(
            AppLanguage.ES to "Notas o intenciones",
            AppLanguage.EN to "Notes or intentions",
            AppLanguage.HI to "नोट्स या इरादे",
            AppLanguage.ZH to "备注或意图说明",
            AppLanguage.AR to "ملاحظات أو نوايا",
            AppLanguage.PT to "Notas ou intenções",
            AppLanguage.FR to "Notes ou intentions"
        ),
        "save" to mapOf(
            AppLanguage.ES to "Guardar",
            AppLanguage.EN to "Save",
            AppLanguage.HI to "सहेजें",
            AppLanguage.ZH to "保存",
            AppLanguage.AR to "حفظ",
            AppLanguage.PT to "Salvar",
            AppLanguage.FR to "Enregistrer"
        ),
        "cancel" to mapOf(
            AppLanguage.ES to "Cancelar",
            AppLanguage.EN to "Cancel",
            AppLanguage.HI to "रद्द करें",
            AppLanguage.ZH to "取消",
            AppLanguage.AR to "إلغاء",
            AppLanguage.PT to "Cancelar",
            AppLanguage.FR to "Annuler"
        ),
        "category" to mapOf(
            AppLanguage.ES to "Categoría",
            AppLanguage.EN to "Category",
            AppLanguage.HI to "श्रेणी",
            AppLanguage.ZH to "类别",
            AppLanguage.AR to "الفئة",
            AppLanguage.PT to "Categoria",
            AppLanguage.FR to "Catégorie"
        ),
        "recurrence" to mapOf(
            AppLanguage.ES to "Recurrencia",
            AppLanguage.EN to "Recurrence",
            AppLanguage.HI to "पुनरावृत्ति",
            AppLanguage.ZH to "重复周期",
            AppLanguage.AR to "التكرار",
            AppLanguage.PT to "Recorrência",
            AppLanguage.FR to "Récurrence"
        ),
        "rec_once" to mapOf(
            AppLanguage.ES to "Una vez",
            AppLanguage.EN to "Once",
            AppLanguage.HI to "एक बार",
            AppLanguage.ZH to "一次性",
            AppLanguage.AR to "مرة واحدة",
            AppLanguage.PT to "Uma vez",
            AppLanguage.FR to "Une fois"
        ),
        "rec_daily" to mapOf(
            AppLanguage.ES to "Diario",
            AppLanguage.EN to "Daily",
            AppLanguage.HI to "दैनिक",
            AppLanguage.ZH to "每日",
            AppLanguage.AR to "يوميًا",
            AppLanguage.PT to "Diário",
            AppLanguage.FR to "Quotidien"
        ),
        "rec_weekly" to mapOf(
            AppLanguage.ES to "Semanal",
            AppLanguage.EN to "Weekly",
            AppLanguage.HI to "साप्ताहिक",
            AppLanguage.ZH to "每周",
            AppLanguage.AR to "أسبوعيًا",
            AppLanguage.PT to "Semanal",
            AppLanguage.FR to "Hebdomadaire"
        ),
        "rec_monthly" to mapOf(
            AppLanguage.ES to "Mensual",
            AppLanguage.EN to "Monthly",
            AppLanguage.HI to "मासिक",
            AppLanguage.ZH to "每月",
            AppLanguage.AR to "شهريًا",
            AppLanguage.PT to "Mensal",
            AppLanguage.FR to "Mensuel"
        ),
        "vibrate" to mapOf(
            AppLanguage.ES to "Vibración",
            AppLanguage.EN to "Vibration",
            AppLanguage.HI to "कंपन",
            AppLanguage.ZH to "振动",
            AppLanguage.AR to "الاهتزاز",
            AppLanguage.PT to "Vibração",
            AppLanguage.FR to "Vibration"
        ),
        "haptic" to mapOf(
            AppLanguage.ES to "Feedback Háptico",
            AppLanguage.EN to "Haptic Feedback",
            AppLanguage.HI to "हैप्टिक फीडबैक",
            AppLanguage.ZH to "触觉反馈",
            AppLanguage.AR to "الردود اللمسية",
            AppLanguage.PT to "Feedback Háptico",
            AppLanguage.FR to "Retour Haptique"
        ),
        "settings" to mapOf(
            AppLanguage.ES to "Ajustes",
            AppLanguage.EN to "Settings",
            AppLanguage.HI to "सेटिंग्स",
            AppLanguage.ZH to "设置",
            AppLanguage.AR to "الإعدادات",
            AppLanguage.PT to "Configurações",
            AppLanguage.FR to "Paramètres"
        ),
        "experience" to mapOf(
            AppLanguage.ES to "Experiencia",
            AppLanguage.EN to "Experience",
            AppLanguage.HI to "अनुभव",
            AppLanguage.ZH to "体验体验",
            AppLanguage.AR to "تجربة الاستخدام",
            AppLanguage.PT to "Experiência",
            AppLanguage.FR to "Expérience"
        ),
        "theme" to mapOf(
            AppLanguage.ES to "Tema",
            AppLanguage.EN to "Theme",
            AppLanguage.HI to "थीम",
            AppLanguage.ZH to "主题色",
            AppLanguage.AR to "المظهر",
            AppLanguage.PT to "Tema",
            AppLanguage.FR to "Thème"
        ),
        "theme_system" to mapOf(
            AppLanguage.ES to "Sistema",
            AppLanguage.EN to "System",
            AppLanguage.HI to "सिस्टम",
            AppLanguage.ZH to "系统默认",
            AppLanguage.AR to "تلقائي النظام",
            AppLanguage.PT to "Sistema",
            AppLanguage.FR to "Système"
        ),
        "theme_light" to mapOf(
            AppLanguage.ES to "Claro",
            AppLanguage.EN to "Light",
            AppLanguage.HI to "हल्का",
            AppLanguage.ZH to "浅色模式",
            AppLanguage.AR to "فاتح",
            AppLanguage.PT to "Claro",
            AppLanguage.FR to "Clair"
        ),
        "theme_dark" to mapOf(
            AppLanguage.ES to "Oscuro",
            AppLanguage.EN to "Dark",
            AppLanguage.HI to "गहरा",
            AppLanguage.ZH to "深色模式",
            AppLanguage.AR to "داكن",
            AppLanguage.PT to "Escuro",
            AppLanguage.FR to "Sombre"
        ),
        "theme_blue" to mapOf(
            AppLanguage.ES to "Azul Océano",
            AppLanguage.EN to "Ocean Blue",
            AppLanguage.HI to "गहरा नीला",
            AppLanguage.ZH to "海洋蓝",
            AppLanguage.AR to "أزرق محيطي",
            AppLanguage.PT to "Azul Oceano",
            AppLanguage.FR to "Bleu Océan"
        ),
        "theme_pink" to mapOf(
            AppLanguage.ES to "Rosa Rubí",
            AppLanguage.EN to "Ruby Rose",
            AppLanguage.HI to "रूबी गुलाबी",
            AppLanguage.ZH to "玫瑰红",
            AppLanguage.AR to "وردي ياقوتي",
            AppLanguage.PT to "Rosa Rubi",
            AppLanguage.FR to "Rose Rubis"
        ),
        "theme_green" to mapOf(
            AppLanguage.ES to "Verde Esmeralda",
            AppLanguage.EN to "Emerald Green",
            AppLanguage.HI to "पन्ना हरा",
            AppLanguage.ZH to "翡翠绿",
            AppLanguage.AR to "أخضر زمردي",
            AppLanguage.PT to "Verde Esmeralda",
            AppLanguage.FR to "Vert Émeraude"
        ),
        "theme_purple" to mapOf(
            AppLanguage.ES to "Violeta Real",
            AppLanguage.EN to "Royal Violet",
            AppLanguage.HI to "शाही बैंगनी",
            AppLanguage.ZH to "皇家紫",
            AppLanguage.AR to "بنفسجي ملكي",
            AppLanguage.PT to "Violeta Real",
            AppLanguage.FR to "Violet Royal"
        ),
        "theme_galactic" to mapOf(
            AppLanguage.ES to "Galáctico",
            AppLanguage.EN to "Galactic",
            AppLanguage.HI to "गैलेक्टिक",
            AppLanguage.ZH to "银河",
            AppLanguage.AR to "مجرّي",
            AppLanguage.PT to "Galáctico",
            AppLanguage.FR to "Galactique"
        ),
        "galactic_locked" to mapOf(
            AppLanguage.ES to "Bloqueado (completa 100 tareas para desbloquear)",
            AppLanguage.EN to "Locked (complete 100 tasks to unlock)",
            AppLanguage.HI to "लॉक है (अनलॉक करने के लिए 100 कार्य पूरे करें)",
            AppLanguage.ZH to "锁定中（完成 100 次以解锁）",
            AppLanguage.AR to "مغلق (أكمل 100 تذكيرًا للفتح)",
            AppLanguage.PT to "Bloqueado (complete 100 tarefas para desbloquear)",
            AppLanguage.FR to "Verrouillé (terminez 100 tâches pour débloquer)"
        ),
        "galactic_unlocked_dialog" to mapOf(
            AppLanguage.ES to "¡Increíble! Has completado 100 intenciones. Has desbloqueado el tema exclusivo Galáctico. ¡El universo te pertenece!",
            AppLanguage.EN to "Amazing! You completed 100 intentions. You've unlocked the exclusive Galactic theme. The universe is yours!",
            AppLanguage.HI to "अद्भुत! आपने 100 इरादे पूरे किए। आपने विशेष गैलेक्टिक थीम अनलॉक कर ली है। ब्रह्मांड आपका है!",
            AppLanguage.ZH to "太棒了！您已完成 100 次。解锁了独家银河主题。宇宙属于你！",
            AppLanguage.AR to "مذهل! لقد أكملت 100 نية. لقد فتحت المظهر المجرّي الحصري. الكون ملكك!",
            AppLanguage.PT to "Incrível! Você completou 100 intenções. Desbloqueou o tema exclusivo Galáctico. O universo é seu!",
            AppLanguage.FR to "Incroyable ! Vous avez complété 100 intentions. Vous avez débloqué le thème exclusif Galactique. L'univers est à vous !"
        ),
        "language" to mapOf(
            AppLanguage.ES to "Idioma",
            AppLanguage.EN to "Language",
            AppLanguage.HI to "भाषा",
            AppLanguage.ZH to "语言",
            AppLanguage.AR to "اللغة",
            AppLanguage.PT to "Idioma",
            AppLanguage.FR to "Langue"
        ),
        "rhythm" to mapOf(
            AppLanguage.ES to "Tu Ritmo",
            AppLanguage.EN to "Your Rhythm",
            AppLanguage.HI to "आपका ताल",
            AppLanguage.ZH to "你的节奏",
            AppLanguage.AR to "إيقاعك",
            AppLanguage.PT to "Seu Ritmo",
            AppLanguage.FR to "Votre Rythme"
        ),
        "agenda" to mapOf(
            AppLanguage.ES to "Mi Agenda",
            AppLanguage.EN to "My Agenda",
            AppLanguage.HI to "मेरी कार्यसूची",
            AppLanguage.ZH to "我的日程",
            AppLanguage.AR to "جدول أعمالي",
            AppLanguage.PT to "Minha Agenda",
            AppLanguage.FR to "Mon Agenda"
        ),
        "privacy" to mapOf(
            AppLanguage.ES to "Privacidad",
            AppLanguage.EN to "Privacy",
            AppLanguage.HI to "गोपनीयता",
            AppLanguage.ZH to "隐私",
            AppLanguage.AR to "الخصوصية",
            AppLanguage.PT to "Privacidade",
            AppLanguage.FR to "Confidentialité"
        ),
        "privacy_promise" to mapOf(
            AppLanguage.ES to "Nuestro compromiso contigo",
            AppLanguage.EN to "Our commitment to you",
            AppLanguage.HI to "हम आपके साथ हमारा वाद",
            AppLanguage.ZH to "我们对您的承诺",
            AppLanguage.AR to "التزامنا معك",
            AppLanguage.PT to "Nosso compromisso com você",
            AppLanguage.FR to "Notre engagement envers vous"
        ),
        "privacy_radical" to mapOf(
            AppLanguage.ES to "Tu información, solo tuya",
            AppLanguage.EN to "Your info, only yours",
            AppLanguage.HI to "आपकी जानकारी, केवल आपकी",
            AppLanguage.ZH to "您的信息，只属于您",
            AppLanguage.AR to "معلوماتك، ملكك فقط",
            AppLanguage.PT to "Suas informações, só suas",
            AppLanguage.FR to "Vos informations, rien qu'à vous"
        ),
        "privacy_desc" to mapOf(
            AppLanguage.ES to "Creemos que tu privacidad es importante. Por eso, A Tiempo está diseñado para que puedas usarlo con tranquilidad, sin anuncios molestos ni seguimiento de ningún tipo.",
            AppLanguage.EN to "We believe your privacy matters. That's why A Tiempo is designed so you can use it with peace of mind, without annoying ads or any kind of tracking.",
            AppLanguage.HI to "हम मानते हैं कि आपकी गोपनीयता महत्वपूर्ण है। इसलिए A तेम्पो को इस तरह डिज़ाइन किया गया है कि आप इसे बिना किसी परेशानी के उपयोग कर सकें, बिना विज्ञापन या ट्रैकिंग के।",
            AppLanguage.ZH to "我们相信您的隐私很重要。因此，A Tiempo 的设计让您可以安心使用，没有烦人的广告或任何形式的跟踪。",
            AppLanguage.AR to "نعتقد أن خصوصيتك مهمة. لهذا السبب، تم تصميم أ تيمبو لتتمكن من استخدامه براحة بال، بدون إعلانات مزعجة أو أي نوع من التتبع.",
            AppLanguage.PT to "Acreditamos que sua privacidade é importante. Por isso, o A Tiempo foi projetado para que você possa usá-lo com tranquilidade, sem anúncios irritantes ou qualquer tipo de rastreamento.",
            AppLanguage.FR to "Nous croyons que votre vie privée est importante. C'est pourquoi A Tiempo est conçu pour que vous puissiez l'utiliser en toute tranquillité, sans publicités gênantes ni aucun suivi."
        ),
        "maintenance" to mapOf(
            AppLanguage.ES to "Mantenimiento",
            AppLanguage.EN to "Maintenance",
            AppLanguage.HI to "रखरखाव",
            AppLanguage.ZH to "系统维护",
            AppLanguage.AR to "الصيانة",
            AppLanguage.PT to "Manutenção",
            AppLanguage.FR to "Maintenance"
        ),
        "battery" to mapOf(
            AppLanguage.ES to "Optimización de batería",
            AppLanguage.EN to "Battery Optimization",
            AppLanguage.HI to "बैटरी अनुकूलन",
            AppLanguage.ZH to "电池耗能优化",
            AppLanguage.AR to "تحسين استهلاك البطارية",
            AppLanguage.PT to "Otimização de bateria",
            AppLanguage.FR to "Optimisation de la batterie"
        ),
        "delete_all" to mapOf(
            AppLanguage.ES to "Borrar todos los datos",
            AppLanguage.EN to "Delete All Data",
            AppLanguage.HI to "सारा डेटा हटाएं",
            AppLanguage.ZH to "清除全部数据",
            AppLanguage.AR to "حذف جميع البيانات",
            AppLanguage.PT to "Apagar todos os dados",
            AppLanguage.FR to "Supprimer toutes les données"
        ),
        "delete_confirm" to mapOf(
            AppLanguage.ES to "¿Estás seguro de borrar todo? Esta acción no se puede deshacer.",
            AppLanguage.EN to "Are you sure you want to delete everything? This cannot be undone.",
            AppLanguage.HI to "क्या आप वाकई सब कुछ हटाना चाहते हैं? इसे वापस नहीं लाया जा सकता।",
            AppLanguage.ZH to "你确定要清除全部数据吗？此操作无法撤销。",
            AppLanguage.AR to "هل أنت متأكد من حذف كل شيء؟ لا يمكن التراجع عن هذا الإجراء.",
            AppLanguage.PT to "Tem certeza de que deseja apagar tudo? Esta ação não pode ser desfeita.",
            AppLanguage.FR to "Êtes-vous sûr de vouloir tout supprimer ? Cette action est irréversible."
        ),
        "version" to mapOf(
            AppLanguage.ES to "Versión 2.1 - Hecho con intención",
            AppLanguage.EN to "Version 2.1 - Made with Intention",
            AppLanguage.HI to "संस्करण 2.1 - इरादे के साथ बनाया गया",
            AppLanguage.ZH to "版本 2.1 - 用心呈现",
            AppLanguage.AR to "الإصدار 2.1 - صُنع بكل نية وأثر",
            AppLanguage.PT to "Versão 2.1 - Feito com intenção",
            AppLanguage.FR to "Version 2.1 - Fait avec intention"
        ),
        "streak" to mapOf(
            AppLanguage.ES to "Racha Actual",
            AppLanguage.EN to "Current Streak",
            AppLanguage.HI to "वर्तमान सिलसिला",
            AppLanguage.ZH to "当前连续",
            AppLanguage.AR to "السلسلة الحالية",
            AppLanguage.PT to "Sequência Atual",
            AppLanguage.FR to "Séquence Actuelle"
        ),
        "max_streak" to mapOf(
            AppLanguage.ES to "Racha Máxima",
            AppLanguage.EN to "Max Streak",
            AppLanguage.HI to "अधिकतम सिलसिला",
            AppLanguage.ZH to "最高连续",
            AppLanguage.AR to "أقصى سلسلة",
            AppLanguage.PT to "Sequência Máxima",
            AppLanguage.FR to "Séquence Maximale"
        ),
        "completions" to mapOf(
            AppLanguage.ES to "Completaciones",
            AppLanguage.EN to "Completions",
            AppLanguage.HI to "पूर्ण कार्य",
            AppLanguage.ZH to "完成次数",
            AppLanguage.AR to "المرات المكتملة",
            AppLanguage.PT to "Conclusões",
            AppLanguage.FR to "Réalisations"
        ),
        "best_time" to mapOf(
            AppLanguage.ES to "Mejor Hora",
            AppLanguage.EN to "Best Time",
            AppLanguage.HI to "सर्वोत्तम समय",
            AppLanguage.ZH to "最佳时段",
            AppLanguage.AR to "أفضل وقت",
            AppLanguage.PT to "Melhor Horário",
            AppLanguage.FR to "Meilleure Heure"
        ),
        "activity_log" to mapOf(
            AppLanguage.ES to "Últimas 5 Actividades",
            AppLanguage.EN to "Last 5 Activities",
            AppLanguage.HI to "अंतिम 5 गतिविधियां",
            AppLanguage.ZH to "最近 5 条活动记录",
            AppLanguage.AR to "آخر 5 نشاطات مسجلة",
            AppLanguage.PT to "Últimas 5 Atividades",
            AppLanguage.FR to "5 Dernières Activités"
        ),
        "active_rems" to mapOf(
            AppLanguage.ES to "Activos",
            AppLanguage.EN to "Active",
            AppLanguage.HI to "सक्रिय",
            AppLanguage.ZH to "启用中",
            AppLanguage.AR to "نشط",
            AppLanguage.PT to "Ativos",
            AppLanguage.FR to "Actifs"
        ),
        "urgent_rems" to mapOf(
            AppLanguage.ES to "Urgentes",
            AppLanguage.EN to "Urgent",
            AppLanguage.HI to "तत्काल",
            AppLanguage.ZH to "紧急",
            AppLanguage.AR to "عاجل",
            AppLanguage.PT to "Urgentes",
            AppLanguage.FR to "Urgents"
        ),
        "pending_rems" to mapOf(
            AppLanguage.ES to "Pendientes",
            AppLanguage.EN to "Pending",
            AppLanguage.HI to "लंबित",
            AppLanguage.ZH to "待办",
            AppLanguage.AR to "معلق",
            AppLanguage.PT to "Pendentes",
            AppLanguage.FR to "En attente"
        ),
        "done" to mapOf(
            AppLanguage.ES to "Hecho",
            AppLanguage.EN to "Done",
            AppLanguage.HI to "हो गया",
            AppLanguage.ZH to "完成",
            AppLanguage.AR to "تم",
            AppLanguage.PT to "Feito",
            AppLanguage.FR to "Fait"
        ),
        "snooze" to mapOf(
            AppLanguage.ES to "Posponer",
            AppLanguage.EN to "Snooze",
            AppLanguage.HI to "स्थगित करें",
            AppLanguage.ZH to "稍后",
            AppLanguage.AR to "تأجيل",
            AppLanguage.PT to "Adiar",
            AppLanguage.FR to "Rappeler"
        ),
        "deactivate" to mapOf(
            AppLanguage.ES to "Desactivar",
            AppLanguage.EN to "Deactivate",
            AppLanguage.HI to "निष्क्रिय करें",
            AppLanguage.ZH to "停用",
            AppLanguage.AR to "إيقاف",
            AppLanguage.PT to "Desativar",
            AppLanguage.FR to "Désactiver"
        ),
        "alert_posponed_warning" to mapOf(
            AppLanguage.ES to "¡Cuidado! Pospones este recordatorio muy seguido.",
            AppLanguage.EN to "Warning! You snooze this reminder very frequently.",
            AppLanguage.HI to "चेतावनी! आप इस अनुस्मारक को बार-बार टाल रहे हैं।",
            AppLanguage.ZH to "警告！您推迟此提醒的次数过于频繁。",
            AppLanguage.AR to "تنبيه! أنت تؤجل هذا التذكير بشكل متكرر.",
            AppLanguage.PT to "Cuidado! Você adia este lembrete com muita frequência.",
            AppLanguage.FR to "Attention ! Vous reportez ce rappel très souvent."
        ),
        "notification_invitation" to mapOf(
            AppLanguage.ES to "Te invitamos a tomarte un momento para cumplir con tu intención.",
            AppLanguage.EN to "We invite you to take a moment to fulfill your intention.",
            AppLanguage.HI to "हम आपको अपने इरादे को पूरा करने के लिए एक क्षण निकालने के लिए आमंत्रित करते हैं।",
            AppLanguage.ZH to "我们邀请您花一点时间来实现您的意图。",
            AppLanguage.AR to "ندعوك لأخذ لحظة لتحقيق نيتك.",
            AppLanguage.PT to "Convidamos você a reservar um momento para cumprir sua intenção.",
            AppLanguage.FR to "Nous vous invitons à prendre un moment pour honorer votre intention."
        ),
        "notification_snooze_10m" to mapOf(
            AppLanguage.ES to "Posponer (10m)",
            AppLanguage.EN to "Snooze (10m)",
            AppLanguage.HI to "स्थगित करें (10 मिनट)",
            AppLanguage.ZH to "稍后 (10分钟)",
            AppLanguage.AR to "تأجيل (10 دقائق)",
            AppLanguage.PT to "Adiar (10min)",
            AppLanguage.FR to "Rappeler (10 min)"
        ),
        "premium_locked" to mapOf(
            AppLanguage.ES to "Bloqueado (completa 50 tareas para desbloquear)",
            AppLanguage.EN to "Locked (complete 50 tasks to unlock)",
            AppLanguage.HI to "लॉक है (अनलॉक करने के लिए 50 कार्य पूरे करें)",
            AppLanguage.ZH to "锁定中（完成 50 次以解锁）",
            AppLanguage.AR to "مغلق (أكمل 50 تذكيرًا للفتح)",
            AppLanguage.PT to "Bloqueado (complete 50 tarefas para desbloquear)",
            AppLanguage.FR to "Verrouillé (terminez 50 tâches pour débloquer)"
        ),
        "premium_unlocked_dialog" to mapOf(
            AppLanguage.ES to "¡Felicitaciones! Has completado 50 intenciones. Desbloqueaste los temas premium (Azul, Rosa, Verde y Violeta) y el historial detallado de completaciones. ¡Sigue así para desbloquear el tema Galáctico a las 100!",
            AppLanguage.EN to "Congratulations! You completed 50 intentions. You've unlocked the premium themes (Ocean Blue, Ruby Rose, Emerald, Royal Violet) and the detailed completions history. Keep going to unlock the Galactic theme at 100!",
            AppLanguage.HI to "बधाई हो! आपने 50 इरादे पूरे किए। आपने प्रीमियम थीम और विस्तृत इतिहास अनलॉक कर लिया है। गैलेक्टिक थीम के लिए 100 तक जारी रखें!",
            AppLanguage.ZH to "恭喜！您已完成 50 次。解锁了高级主题色与详细的完成记录历史。继续努力到 100 次解锁银河主题！",
            AppLanguage.AR to "تهانينا! لقد أكملت 50 نية. لقد فتحت المظاهر المميزة وسجل الاكتمال التفصيلي. واصل حتى 100 لفتح المظهر المجرّي!",
            AppLanguage.PT to "Parabéns! Você completou 50 intenções. Desbloqueou os temas premium e o histórico detalhado de conclusões. Continue até 100 para desbloquear o tema Galáctico!",
            AppLanguage.FR to "Félicitations ! Vous avez complété 50 intentions. Vous avez débloqué les thèmes premium et l'historique détaillé. Continuez jusqu'à 100 pour débloquer le thème Galactique !"
        ),
        "accept_disclaimer" to mapOf(
            AppLanguage.ES to "Entiendo y acepto",
            AppLanguage.EN to "I understand and accept",
            AppLanguage.HI to "मैं समझता हूँ और स्वीकार करता हूँ",
            AppLanguage.ZH to "我理解并接受",
            AppLanguage.AR to "أفهم وأقبل",
            AppLanguage.PT to "Entendo e aceito",
            AppLanguage.FR to "Je comprends et j'accepte"
        ),
        "congratulations" to mapOf(
            AppLanguage.ES to "¡Felicitaciones!",
            AppLanguage.EN to "Congratulations!",
            AppLanguage.HI to "बधाई हो!",
            AppLanguage.ZH to "恭喜！",
            AppLanguage.AR to "تهانينا!",
            AppLanguage.PT to "Parabéns!",
            AppLanguage.FR to "Félicitations !"
        ),
        "lang_confirm_title" to mapOf(
            AppLanguage.ES to "Cambiar Idioma",
            AppLanguage.EN to "Change Language",
            AppLanguage.HI to "भाषा बदलें",
            AppLanguage.ZH to "更改语言",
            AppLanguage.AR to "تغيير اللغة",
            AppLanguage.PT to "Alterar Idioma",
            AppLanguage.FR to "Changer de Langue"
        ),
        "lang_confirm_desc" to mapOf(
            AppLanguage.ES to "¿Estás seguro de que quieres cambiar el idioma?",
            AppLanguage.EN to "Are you sure you want to change the language?",
            AppLanguage.HI to "क्या आप वाकई भाषा बदलना चाहते हैं?",
            AppLanguage.ZH to "您确定要更改语言吗？",
            AppLanguage.AR to "هل أنت متأكد من تغيير اللغة؟",
            AppLanguage.PT to "Tem certeza de que deseja alterar o idioma?",
            AppLanguage.FR to "Êtes-vous sûr de vouloir changer de langue ?"
        ),
        "yes" to mapOf(
            AppLanguage.ES to "Sí",
            AppLanguage.EN to "Yes",
            AppLanguage.HI to "हाँ",
            AppLanguage.ZH to "是的",
            AppLanguage.AR to "نعم",
            AppLanguage.PT to "Sim",
            AppLanguage.FR to "Oui"
        ),
        "no" to mapOf(
            AppLanguage.ES to "No",
            AppLanguage.EN to "No",
            AppLanguage.HI to "नहीं",
            AppLanguage.ZH to "不",
            AppLanguage.AR to "لا",
            AppLanguage.PT to "Não",
            AppLanguage.FR to "Non"
        ),
        "local_commitment" to mapOf(
            AppLanguage.ES to "100% Local",
            AppLanguage.EN to "100% Local",
            AppLanguage.HI to "100% स्थानीय",
            AppLanguage.ZH to "100% 离线本地",
            AppLanguage.AR to "مستضاف محليًا 100٪",
            AppLanguage.PT to "100% Local",
            AppLanguage.FR to "100% Local"
        ),
        "local_commitment_desc" to mapOf(
            AppLanguage.ES to "Tus recordatorios, notas e historial se guardan en tu teléfono. Tus datos personales nunca salen de tu dispositivo.",
            AppLanguage.EN to "Your reminders, notes, and history are saved on your phone. Your personal data never leaves your device.",
            AppLanguage.HI to "आपके अनुस्मारक, नोट्स और इतिहास आपके फ़ोन पर सहेजे जाते हैं। आपका व्यक्तिगत डेटा कभी आपके डिवाइस से नहीं जाता।",
            AppLanguage.ZH to "您的提醒、笔记和记录保存在您的手机上。您的个人数据绝不会离开您的设备。",
            AppLanguage.AR to "تذكيراتك وملاحظاتك وسجلك تُحفظ على هاتفك. بياناتك الشخصية لا تغادر جهازك أبدًا.",
            AppLanguage.PT to "Seus lembretes, notas e histórico são salvos no seu telefone. Seus dados pessoais jamais saem do seu dispositivo.",
            AppLanguage.FR to "Vos rappels, notes et historique sont sauvegardés sur votre téléphone. Vos données personnelles ne quittent jamais votre appareil."
        ),
        "zero_tracking" to mapOf(
            AppLanguage.ES to "Sin seguimiento",
            AppLanguage.EN to "No Tracking",
            AppLanguage.HI to "कोई ट्रैकिंग नहीं",
            AppLanguage.ZH to "无跟踪",
            AppLanguage.AR to "لا تتبع",
            AppLanguage.PT to "Sem rastreamento",
            AppLanguage.FR to "Aucun suivi"
        ),
        "zero_tracking_desc" to mapOf(
            AppLanguage.ES to "No vigilamos tus hábitos ni vendemos tu información. Tu privacidad está protegida.",
            AppLanguage.EN to "We don't watch your habits or sell your information. Your privacy is protected.",
            AppLanguage.HI to "हम आपकी आदतों पर नज़र नहीं रखते या आपकी जानकारी नहीं बेचते। आपकी गोपनीयता सुरक्षित है।",
            AppLanguage.ZH to "我们不会窥探您的习惯或出售您的信息。您的隐私受到保护。",
            AppLanguage.AR to "نحن لا نراقب عاداتك ولا نبيع معلوماتك. خصوصيتك محمية.",
            AppLanguage.PT to "Não monitoramos seus hábitos nem vendemos suas informações. Sua privacidade está protegida.",
            AppLanguage.FR to "Nous ne surveillons pas vos habitudes ni ne vendons vos informations. Votre vie privée est protégée."
        ),
        "no_servers" to mapOf(
            AppLanguage.ES to "Sin cuentas ni registro",
            AppLanguage.EN to "No accounts or sign-ups",
            AppLanguage.HI to "कोई खाता या पंजीकरण नहीं",
            AppLanguage.ZH to "无需注册或登录",
            AppLanguage.AR to "لا حسابات أو تسجيل",
            AppLanguage.PT to "Sem contas nem registro",
            AppLanguage.FR to "Sans compte ni inscription"
        ),
        "no_servers_desc" to mapOf(
            AppLanguage.ES to "Sin cuentas, sin contraseñas, sin registro. Funciona inmediatamente sin internet.",
            AppLanguage.EN to "No accounts, no passwords, no sign-ups. Works instantly without internet.",
            AppLanguage.HI to "कोई खाता नहीं, कोई पासवर्ड नहीं, कोई साइन-अप नहीं। बिना इंटरनेट के तुरंत काम करता है।",
            AppLanguage.ZH to "无需登录，免去繁琐注册。打开即用，在无信号环境中依然运转如常。",
            AppLanguage.AR to "لا حسابات، لا كلمات مرور، لا تسجيل. يعمل فورًا بدون إنترنت.",
            AppLanguage.PT to "Sem contas, sem senhas, sem registro. Funciona imediatamente sem internet.",
            AppLanguage.FR to "Pas de comptes, pas de mots de passe, pas d'inscription. Fonctionne immédiatement sans Internet."
        ),
        "privacy_quote" to mapOf(
            AppLanguage.ES to "\"La privacidad no es una opción, es un derecho fundamental.\"",
            AppLanguage.EN to "\"Privacy is not an option, it is a fundamental right.\"",
            AppLanguage.HI to "\"गोपनीयता कोई विकल्प नहीं है, यह एक मौलिक अधिकार है।\"",
            AppLanguage.ZH to "“隐私并非一种选择，而是一项基本权利。”",
            AppLanguage.AR to "\"الخصوصية ليست خيارًا، بل هي حق أساسي من حقوقك.\"",
            AppLanguage.PT to "\"A privacidade não é uma opção, é um direito fundamental.\"",
            AppLanguage.FR to "« La vie privée n'est pas une option, c'est un droit fondamental. »"
        ),
        "category_salud" to mapOf(AppLanguage.ES to "Salud", AppLanguage.EN to "Health", AppLanguage.HI to "स्वास्थ्य", AppLanguage.ZH to "健康", AppLanguage.AR to "الصحة", AppLanguage.PT to "Saúde", AppLanguage.FR to "Santé"),
        "category_trabajo" to mapOf(AppLanguage.ES to "Trabajo", AppLanguage.EN to "Work", AppLanguage.HI to "काम", AppLanguage.ZH to "工作", AppLanguage.AR to "العمل", AppLanguage.PT to "Trabalho", AppLanguage.FR to "Travail"),
        "category_estudio" to mapOf(AppLanguage.ES to "Estudio", AppLanguage.EN to "Study", AppLanguage.HI to "अध्ययन", AppLanguage.ZH to "学习", AppLanguage.AR to "الدراسة", AppLanguage.PT to "Estudo", AppLanguage.FR to "Études"),
        "category_hogar" to mapOf(AppLanguage.ES to "Hogar", AppLanguage.EN to "Home", AppLanguage.HI to "घर", AppLanguage.ZH to "家务", AppLanguage.AR to "المنزل", AppLanguage.PT to "Lar", AppLanguage.FR to "Maison"),
        "category_finanzas" to mapOf(AppLanguage.ES to "Finanzas", AppLanguage.EN to "Finance", AppLanguage.HI to "वित्त", AppLanguage.ZH to "财务", AppLanguage.AR to "المالية", AppLanguage.PT to "Finanças", AppLanguage.FR to "Finances"),
        "category_social" to mapOf(AppLanguage.ES to "Social", AppLanguage.EN to "Social", AppLanguage.HI to "सामाजिक", AppLanguage.ZH to "社交", AppLanguage.AR to "العلاقات", AppLanguage.PT to "Social", AppLanguage.FR to "Social"),
        "category_otros" to mapOf(AppLanguage.ES to "Otros", AppLanguage.EN to "Others", AppLanguage.HI to "अन्य", AppLanguage.ZH to "其他", AppLanguage.AR to "أخرى", AppLanguage.PT to "Outros", AppLanguage.FR to "Autres"),
        "sponsored_ad" to mapOf(
            AppLanguage.ES to "Anuncio Patrocinado",
            AppLanguage.EN to "Sponsored Ad",
            AppLanguage.HI to "प्रायोजित विज्ञापन",
            AppLanguage.ZH to "赞助广告",
            AppLanguage.AR to "إعلان ممول",
            AppLanguage.PT to "Anúncio Patrocinado",
            AppLanguage.FR to "Publicité Sponsorisée"
        ),
        "ad_space_available" to mapOf(
            AppLanguage.ES to "Espacio disponible para anunciantes",
            AppLanguage.EN to "Ad space available for partners",
            AppLanguage.HI to "भागीदारों के लिए विज्ञापन स्थान उपलब्ध",
            AppLanguage.ZH to "广告位招商合作",
            AppLanguage.AR to "مساحة إعلانية متاحة للشركاء",
            AppLanguage.PT to "Espaço disponível para anunciantes",
            AppLanguage.FR to "Espace publicitaire disponible pour les partenaires"
        ),
        "ad_space_desc" to mapOf(
            AppLanguage.ES to "Este espacio está reservado para anunciantes e integraciones respetuosas con el bienestar.\n\nPara anunciarte en A Tiempo, contáctanos en:",
            AppLanguage.EN to "This space is reserved for advertisers and wellness-respecting integrations.\n\nTo advertise in A Tiempo, contact us at:",
            AppLanguage.HI to "यह स्थान विज्ञापनदाताओं और कल्याण-सम्मानजनक एकीकरण के लिए आरक्षित है।\n\nA Tiempo में विज्ञापन देने के लिए, हमसे संपर्क करें:",
            AppLanguage.ZH to "此空间保留给广告商和尊重健康的合作伙伴。\n\n要在 A Tiempo 投放广告，请联系我们：",
            AppLanguage.AR to "هذه المساحة محجوزة للمعلنين والتكاملات المحترمة للصحة.\n\nللإعلان في أ تيمبو، اتصل بنا على:",
            AppLanguage.PT to "Este espaço está reservado para anunciantes e integrações respeitosas com o bem-estar.\n\nPara anunciar no A Tiempo, entre em contato:",
            AppLanguage.FR to "Cet espace est réservé aux annonceurs et aux intégrations respectueuses du bien-être.\n\nPour faire de la publicité dans A Tiempo, contactez-nous à :"
        ),
        "resume_streak" to mapOf(
            AppLanguage.ES to "Reanudar Racha",
            AppLanguage.EN to "Resume Streak",
            AppLanguage.HI to "सिलसिला फिर शुरू करें",
            AppLanguage.ZH to "恢复连续",
            AppLanguage.AR to "استئناف السلسلة",
            AppLanguage.PT to "Retomar Sequência",
            AppLanguage.FR to "Reprendre la Séquence"
        ),
        "resume_streak_desc" to mapOf(
            AppLanguage.ES to "¡Oh no! Perdiste tu racha ayer. Mira un anuncio completo para recuperarla.",
            AppLanguage.EN to "Oh no! You lost your streak yesterday. Watch a complete ad to recover it.",
            AppLanguage.HI to "ओह नहीं! आप कल अपना सिलसिला खो बैठे। इसे पुनः प्राप्त करने के लिए एक पूरा विज्ञापन देखें।",
            AppLanguage.ZH to "哎呀！你昨天中断了连续。观看完整广告即可恢复它。",
            AppLanguage.AR to "أوه لا! لقد فقدت سلسلتك بالأمس. شاهد إعلاناً كاملاً لاستعادتها.",
            AppLanguage.PT to "Ah não! Você perdeu sua sequência ontem. Assista a um anúncio completo para recuperá-la.",
            AppLanguage.FR to "Oh non ! Vous avez perdu votre séquence hier. Regardez une publicité complète pour la récupérer."
        ),
        "ad_loading" to mapOf(
            AppLanguage.ES to "Cargando anuncio...",
            AppLanguage.EN to "Loading ad...",
            AppLanguage.HI to "विज्ञापन लोड हो रहा है...",
            AppLanguage.ZH to "正在加载广告...",
            AppLanguage.AR to "جاري تحميل الإعلان...",
            AppLanguage.PT to "Carregando anúncio...",
            AppLanguage.FR to "Chargement de la publicité..."
        ),
        "privacy_promise" to mapOf(
            AppLanguage.ES to "COMPROMISO A TIEMPO",
            AppLanguage.EN to "A TIEMPO COMMITMENT",
            AppLanguage.HI to "ए टिएम्पo प्रतिबद्धता",
            AppLanguage.ZH to "A TIEMPO 承诺",
            AppLanguage.AR to "التزام أ تيمبو",
            AppLanguage.PT to "Nosso compromisso com você",
            AppLanguage.FR to "Notre engagement envers vous"
        ),
        "privacy_radical" to mapOf(
            AppLanguage.ES to "Privacidad Radical",
            AppLanguage.EN to "Radical Privacy",
            AppLanguage.HI to "कट्टरपंथी गोपनीयता",
            AppLanguage.ZH to "彻底的隐私保护",
            AppLanguage.AR to "خصوصية مطلقة",
            AppLanguage.PT to "Suas informações, só suas",
            AppLanguage.FR to "Vos informations, rien qu'à vous"
        ),
        "privacy_desc" to mapOf(
            AppLanguage.ES to "Tus recordatorios, notas y hábitos se guardan en tu teléfono. A Tiempo es gratuita y de código abierto, sin anuncios ni telemetría.",
            AppLanguage.EN to "Your reminders, notes and habits stay on your phone. A Tiempo is free and open source, without ads or telemetry.",
            AppLanguage.HI to "आपके अनुस्मारक, नोट्स और आदतें आपके फ़ोन पर रहती हैं। A Tiempo मुफ़्त और ओपन सोर्स है, बिना विज्ञापन या टेलीमेट्री के।",
            AppLanguage.ZH to "您的提醒、笔记和习惯保存在手机上。A Tiempo 免费开源，没有广告或遥测。",
            AppLanguage.AR to "تبقى تذكيراتك وملاحظاتك وعاداتك على هاتفك. التطبيق مجاني ومفتوح المصدر، بلا إعلانات أو قياس عن بُعد.",
            AppLanguage.PT to "Seus lembretes, notas e hábitos ficam no telefone. A Tiempo é gratuito e de código aberto, sem anúncios ou telemetria.",
            AppLanguage.FR to "Vos rappels, notes et habitudes restent sur votre téléphone. A Tiempo est gratuit et open source, sans publicité ni télémétrie."
        ),
        "privacy_title_1" to mapOf(
            AppLanguage.ES to "1. Funciona sin internet",
            AppLanguage.EN to "1. Works Without Internet",
            AppLanguage.HI to "1. बिना इंटरनेट के काम करता है",
            AppLanguage.ZH to "1. 无需联网运行",
            AppLanguage.AR to "1. يعمل بدون إنترنت",
            AppLanguage.PT to "1. Sem conexão necessária",
            AppLanguage.FR to "1. Fonctionne sans Internet"
        ),
        "privacy_desc_1" to mapOf(
            AppLanguage.ES to "Todo funciona directamente en tu teléfono. No necesitas internet para usarla y nadie más puede ver lo que haces aquí.",
            AppLanguage.EN to "Everything works directly on your phone. You don't need internet to use it and no one else can see what you do here.",
            AppLanguage.HI to "सब कुछ सीधे आपके फ़ोन पर काम करता है। इसका उपयोग करने के लिए आपको इंटरनेट की आवश्यकता नहीं है और आपके अलावा कोई और यह नहीं देख सकता कि आप यहाँ क्या करते हैं।",
            AppLanguage.ZH to "一切都在您的手机上直接运行。您无需网络即可使用，其他人也看不到您在这里做什么。",
            AppLanguage.AR to "كل شيء يعمل مباشرة على هاتفك. لا تحتاج إلى إنترنت لاستخدامه، ولا يمكن لأي شخص آخر رؤية ما تفعله هنا.",
            AppLanguage.PT to "Tudo funciona diretamente no seu telefone. Você não precisa de internet e ninguém mais pode ver o que você faz aqui.",
            AppLanguage.FR to "Tout fonctionne directement sur votre téléphone. Vous n'avez pas besoin d'Internet pour l'utiliser et personne d'autre ne peut voir ce que vous faites ici."
        ),
        "privacy_title_2" to mapOf(
            AppLanguage.ES to "2. Tus datos se quedan contigo",
            AppLanguage.EN to "2. Your Data Stays with You",
            AppLanguage.HI to "2. आपका डेटा आपके पास रहता है",
            AppLanguage.ZH to "2. 您的数据保存在本地",
            AppLanguage.AR to "2. بياناتك تبقى معك",
            AppLanguage.PT to "2. Seus dados são privados",
            AppLanguage.FR to "2. Vos données restent avec vous"
        ),
        "privacy_desc_2" to mapOf(
            AppLanguage.ES to "Tus recordatorios, notas e historial se guardan solo en tu teléfono. Nadie fuera de tu dispositivo puede verlos, ni siquiera nosotros.",
            AppLanguage.EN to "Your reminders, notes, and history are saved only on your phone. No one outside your device can see them, not even us.",
            AppLanguage.HI to "आपके अनुस्मारक, नोट्स और इतिहास केवल आपके फ़ोन में सहेजे जाते हैं। आपके डिवाइस के बाहर कोई भी उन्हें नहीं देख सकता, हम भी नहीं।",
            AppLanguage.ZH to "您的提醒、笔记和历史记录仅保存在您的手机上。您设备之外的任何人都无法看到它们，包括我们。",
            AppLanguage.AR to "تذكيراتك وملاحظاتك وسجلك تُحفظ فقط على هاتفك. لا يمكن لأي شخص خارج جهازك رؤيتها، ولا حتى نحن.",
            AppLanguage.PT to "Seus lembretes e notas são salvos apenas no seu telefone. Ninguém mais pode vê-los, nem mesmo nós.",
            AppLanguage.FR to "Vos rappels, notes et historique sont sauvegardés uniquement sur votre téléphone. Personne en dehors de votre appareil ne peut les voir, pas même nous."
        ),
        "privacy_title_3" to mapOf(
            AppLanguage.ES to "3. Sin anuncios ni rastreo",
            AppLanguage.EN to "3. No ads or tracking",
            AppLanguage.HI to "3. कोई विज्ञापन या ट्रैकिंग नहीं",
            AppLanguage.ZH to "3. 无广告，无追踪",
            AppLanguage.AR to "3. بلا إعلانات أو تتبع",
            AppLanguage.PT to "3. Sem anúncios ou rastreamento",
            AppLanguage.FR to "3. Sans publicité ni suivi"
        ),
        "privacy_desc_3" to mapOf(
            AppLanguage.ES to "La app no incluye publicidad, analítica ni envío automático de fallos. Las recompensas se desbloquean con el uso, sin pagos ni anuncios.",
            AppLanguage.EN to "The app includes no advertising, analytics or automatic crash reporting. Rewards unlock through use, without payments or ads.",
            AppLanguage.HI to "ऐप में विज्ञापन, एनालिटिक्स या स्वचालित क्रैश रिपोर्ट नहीं हैं। पुरस्कार उपयोग से खुलते हैं, बिना भुगतान या विज्ञापन के।",
            AppLanguage.ZH to "应用不包含广告、分析或自动崩溃报告。奖励通过使用解锁，无需付款或观看广告。",
            AppLanguage.AR to "لا يتضمن التطبيق إعلانات أو تحليلات أو تقارير أعطال تلقائية. تُفتح المكافآت بالاستخدام دون دفع أو إعلانات.",
            AppLanguage.PT to "O app não inclui publicidade, análises ou envio automático de falhas. As recompensas são desbloqueadas com o uso, sem pagamentos ou anúncios.",
            AppLanguage.FR to "L'app ne contient ni publicité, ni analyse, ni rapport automatique de plantage. Les récompenses se débloquent à l'usage, sans paiement ni publicité."
        ),
        "privacy_title_4" to mapOf(
            AppLanguage.ES to "4. Sin cuentas de usuario",
            AppLanguage.EN to "4. No User Accounts",
            AppLanguage.HI to "4. कोई उपयोगकर्ता खाता नहीं",
            AppLanguage.ZH to "4. 无需注册用户账号",
            AppLanguage.AR to "4. بدون حسابات مستخدمين",
            AppLanguage.PT to "4. Sem contas nem senhas",
            AppLanguage.FR to "4. Pas de comptes utilisateur"
        ),
        "privacy_desc_4" to mapOf(
            AppLanguage.ES to "No necesitas registrarte ni crear una cuenta. Descargas la app y la usas directamente, cuando quieras.",
            AppLanguage.EN to "You don't need to register or create an account. Download the app and use it directly, whenever you want.",
            AppLanguage.HI to "आपको पंजीकरण करने या खाता बनाने की आवश्यकता नहीं है। ऐप डाउनलोड करें और सीधे उपयोग करें, जब चाहें।",
            AppLanguage.ZH to "您无需注册或创建账户。下载应用后可直接使用，随时随地。",
            AppLanguage.AR to "لا تحتاج إلى التسجيل أو إنشاء حساب. قم بتنزيل التطبيق واستخدمه مباشرة، متى شئت.",
            AppLanguage.PT to "Você não precisa se registrar nem criar uma conta. Baixe o aplicativo e use-o diretamente, quando quiser e como quiser.",
            AppLanguage.FR to "Vous n'avez pas besoin de vous inscrire ni de créer un compte. Téléchargez l'application et utilisez-la directement, quand vous voulez."
        ),
        "max_days_short" to mapOf(
            AppLanguage.ES to "Máx: %d d",
            AppLanguage.EN to "Max: %d d",
            AppLanguage.HI to "अधिकतम: %d दिन",
            AppLanguage.ZH to "最高: %d 天",
            AppLanguage.AR to "الأقصى: %d ي",
            AppLanguage.PT to "Máx: %d d",
            AppLanguage.FR to "Max : %d j"
        ),
        "optimal_focus" to mapOf(
            AppLanguage.ES to "Foco óptimo",
            AppLanguage.EN to "Optimal focus",
            AppLanguage.HI to "इष्टतम फोकस",
            AppLanguage.ZH to "最佳专注",
            AppLanguage.AR to "التركيز الأمثل",
            AppLanguage.PT to "Foco ideal",
            AppLanguage.FR to "Focus optimal"
        ),
        "complete_first_reminder_desc" to mapOf(
            AppLanguage.ES to "Completa tu primer recordatorio para ver tu ritmo.",
            AppLanguage.EN to "Complete your first reminder to see your rhythm.",
            AppLanguage.HI to "अपनी लय देखने के लिए अपना पहला अनुस्मारक पूरा करें।",
            AppLanguage.ZH to "完成您的第一个提醒以查看您的律动。",
            AppLanguage.AR to "أكمل تذكيرك الأول لترى إيقاعك.",
            AppLanguage.PT to "Complete seu primeiro lembrete para ver seu ritmo.",
            AppLanguage.FR to "Complétez votre premier rappel pour voir votre rythme."
        ),
        "detailed_history" to mapOf(
            AppLanguage.ES to "Historial Detallado",
            AppLanguage.EN to "Detailed History",
            AppLanguage.HI to "विस्तृत इतिहास",
            AppLanguage.ZH to "详细历史记录",
            AppLanguage.AR to "السجل التفصيلي",
            AppLanguage.PT to "Histórico Detalhado",
            AppLanguage.FR to "Historique Détaillé"
        ),
        "history_locked" to mapOf(
            AppLanguage.ES to "Historial Premium Bloqueado",
            AppLanguage.EN to "Premium History Locked",
            AppLanguage.HI to "प्रीमियम इतिहास लॉक है",
            AppLanguage.ZH to "高级历史记录已锁定",
            AppLanguage.AR to "السجل المميز مقفل",
            AppLanguage.PT to "Histórico Premium Bloqueado",
            AppLanguage.FR to "Historique Premium Verrouillé"
        ),
        "history_locked_desc" to mapOf(
            AppLanguage.ES to "Alcanza 50 completaciones totales para desbloquear tu historial completo y detallado.\nProgreso: %d/50",
            AppLanguage.EN to "Reach 50 total completions to unlock your full and detailed history.\nProgress: %d/50",
            AppLanguage.HI to "अपना पूरा और विस्तृत इतिहास अनलॉक करने के लिए कुल 50 पूर्ण कार्य प्राप्त करें।\nप्रगति: %d/50",
            AppLanguage.ZH to "累计完成 50 次以解锁完整详细的历史记录。\n当前进度: %d/50",
            AppLanguage.AR to "أكمل 50 مرة في المجموع لفتح سجل نشاطاتك الكامل والتفصيلي.\nالتقدم: %d/50",
            AppLanguage.PT to "Atinga 50 conclusões totais para desbloquear seu histórico completo e detalhado.\nProgresso: %d/50",
            AppLanguage.FR to "Atteignez 50 réalisations totales pour débloquer votre historique complet et détaillé.\nProgrès : %d/50"
        ),
        "no_history_yet" to mapOf(
            AppLanguage.ES to "Sin registros aún en el historial.",
            AppLanguage.EN to "No records in the history yet.",
            AppLanguage.HI to "इतिहास में अभी तक कोई रिकॉर्ड नहीं है।",
            AppLanguage.ZH to "历史记录中尚无数据。",
            AppLanguage.AR to "لا توجد سجلات في السجل بعد.",
            AppLanguage.PT to "Nenhum registro no histórico ainda.",
            AppLanguage.FR to "Aucun enregistrement dans l'historique pour l'instant."
        ),
        "reminder_log_format" to mapOf(
            AppLanguage.ES to "Recordatorio #%d - %s",
            AppLanguage.EN to "Reminder #%d - %s",
            AppLanguage.HI to "अनुस्मारक #%d - %s",
            AppLanguage.ZH to "提醒 #%d - %s",
            AppLanguage.AR to "تذكير #%d - %s",
            AppLanguage.PT to "Lembrete #%d - %s",
            AppLanguage.FR to "Rappel n°%d - %s"
        ),
        "sort_order" to mapOf(
            AppLanguage.ES to "Orden:",
            AppLanguage.EN to "Sort:",
            AppLanguage.HI to "क्रम:",
            AppLanguage.ZH to "排序:",
            AppLanguage.AR to "الترتيب:",
            AppLanguage.PT to "Ordem:",
            AppLanguage.FR to "Ordre :"
        ),
        "ok" to mapOf(
            AppLanguage.ES to "Aceptar",
            AppLanguage.EN to "OK",
            AppLanguage.HI to "ठीक है",
            AppLanguage.ZH to "确定",
            AppLanguage.AR to "حسناً",
            AppLanguage.PT to "OK",
            AppLanguage.FR to "OK"
        ),
        "intentions_of_day" to mapOf(
            AppLanguage.ES to "Intenciones del día (%s)",
            AppLanguage.EN to "Intentions of the day (%s)",
            AppLanguage.HI to "दिन के इरादे (%s)",
            AppLanguage.ZH to "当日意图 (%s)",
            AppLanguage.AR to "نوايا اليوم (%s)",
            AppLanguage.PT to "Intenções do dia (%s)",
            AppLanguage.FR to "Intentions du jour (%s)"
        ),
        "no_intentions_today" to mapOf(
            AppLanguage.ES to "Sin intenciones programadas para este día.",
            AppLanguage.EN to "No intentions scheduled for this day.",
            AppLanguage.HI to "इस दिन के लिए कोई इरादा निर्धारित नहीं है।",
            AppLanguage.ZH to "今天没有安排任何意图。",
            AppLanguage.AR to "لا توجد نوايا مجدولة لهذا اليوم.",
            AppLanguage.PT to "Nenhuma intenção programada para este dia.",
            AppLanguage.FR to "Aucune intention programmée pour ce jour."
        ),
        "your_space" to mapOf(
            AppLanguage.ES to "Tu Espacio",
            AppLanguage.EN to "Your Space",
            AppLanguage.HI to "आपका स्थान",
            AppLanguage.ZH to "你的空间",
            AppLanguage.AR to "مساحتك",
            AppLanguage.PT to "Seu Espaço",
            AppLanguage.FR to "Votre Espace"
        ),
        "clear_all" to mapOf(
            AppLanguage.ES to "Borrar Todo",
            AppLanguage.EN to "Clear All",
            AppLanguage.HI to "सभी मिटाएं",
            AppLanguage.ZH to "清除全部",
            AppLanguage.AR to "مسح الكل",
            AppLanguage.PT to "Limpar Tudo",
            AppLanguage.FR to "Tout Effacer"
        ),
        "privacy_subtitle" to mapOf(
            AppLanguage.ES to "Descubre nuestro compromiso offline",
            AppLanguage.EN to "Discover our offline commitment",
            AppLanguage.HI to "हमारे ऑफलाइन प्रतिबद्धता के बारे में जानें",
            AppLanguage.ZH to "了解我们的离线承诺",
            AppLanguage.AR to "اكتشف الالتزام الخاص بنا بدون إنترنت",
            AppLanguage.PT to "Descubra nosso compromisso offline",
            AppLanguage.FR to "Découvrez notre engagement hors ligne"
        ),
        "disclaimer_subtitle" to mapOf(
            AppLanguage.ES to "Términos, límites y filosofía",
            AppLanguage.EN to "Terms, limits and philosophy",
            AppLanguage.HI to "शर्तें, सीमाएं और दर्शन",
            AppLanguage.ZH to "条款、限制和理念",
            AppLanguage.AR to "الشروط والحدود والفلسفة",
            AppLanguage.PT to "Termos, limites e filosofia",
            AppLanguage.FR to "Conditions, limites et philosophie"
        ),
        "rhythm_subtitle" to mapOf(
            AppLanguage.ES to "Estadísticas, rachas e historial",
            AppLanguage.EN to "Statistics, streaks and history",
            AppLanguage.HI to "आंकड़े, सिलसिले और इतिहास",
            AppLanguage.ZH to "统计、连续和历史记录",
            AppLanguage.AR to "إحصاءات، سلاسل، ومحفوظات",
            AppLanguage.PT to "Estatísticas, sequências e histórico",
            AppLanguage.FR to "Statistiques, séquences et historique"
        ),
        "agenda_subtitle" to mapOf(
            AppLanguage.ES to "Planificador mensual de intenciones",
            AppLanguage.EN to "Monthly intention planner",
            AppLanguage.HI to "मासिक इरादा योजनाकर्ता",
            AppLanguage.ZH to "月度意图规划器",
            AppLanguage.AR to "مخطط النوايا الشهري",
            AppLanguage.PT to "Planejador mensal de intenções",
            AppLanguage.FR to "Planificateur mensuel d'intentions"
        ),
        "battery_subtitle" to mapOf(
            AppLanguage.ES to "Asegura el funcionamiento de las alarmas",
            AppLanguage.EN to "Ensures alarms work properly",
            AppLanguage.HI to "अलार्म के सही कार्य के लिए",
            AppLanguage.ZH to "确保闹钟正常工作",
            AppLanguage.AR to "يضمن عمل المنبهات بشكل صحيح",
            AppLanguage.PT to "Garante o funcionamento dos alarmes",
            AppLanguage.FR to "Assure le bon fonctionnement des alarmes"
        ),
        "notifications_title" to mapOf(
            AppLanguage.ES to "Notificaciones",
            AppLanguage.EN to "Notifications",
            AppLanguage.HI to "सूचनाएं",
            AppLanguage.ZH to "通知",
            AppLanguage.AR to "الإشعارات",
            AppLanguage.PT to "Notificações",
            AppLanguage.FR to "Notifications"
        ),
        "notifications_on" to mapOf(
            AppLanguage.ES to "Activadas",
            AppLanguage.EN to "On",
            AppLanguage.HI to "चालू",
            AppLanguage.ZH to "已开启",
            AppLanguage.AR to "مُفعّلة",
            AppLanguage.PT to "Ativadas",
            AppLanguage.FR to "Activées"
        ),
        "notifications_off" to mapOf(
            AppLanguage.ES to "Desactivadas",
            AppLanguage.EN to "Off",
            AppLanguage.HI to "बंद",
            AppLanguage.ZH to "已关闭",
            AppLanguage.AR to "مُعطّلة",
            AppLanguage.PT to "Desativadas",
            AppLanguage.FR to "Désactivées"
        ),
        "notification_dialog_title" to mapOf(
            AppLanguage.ES to "Notificaciones desactivadas",
            AppLanguage.EN to "Notifications disabled",
            AppLanguage.HI to "सूचनाएं अक्षम हैं",
            AppLanguage.ZH to "通知已关闭",
            AppLanguage.AR to "الإشعارات غير مفعلة",
            AppLanguage.PT to "Notificações desativadas",
            AppLanguage.FR to "Notifications désactivées"
        ),
        "notification_dialog_message" to mapOf(
            AppLanguage.ES to "Para recibir tus recordatorios a tiempo, activa las notificaciones. ¿Quieres ir a configuración?",
            AppLanguage.EN to "To receive your reminders on time, please enable notifications. Go to settings?",
            AppLanguage.HI to "समय पर अनुस्मारक प्राप्त करने के लिए सूचनाएं चालू करें। सेटिंग्स पर जाएं?",
            AppLanguage.ZH to "为了准时收到提醒，请开启通知。前往设置？",
            AppLanguage.AR to "لتلقي تذكيراتك في الوقت المحدد، يرجى تفعيل الإشعارات. هل تريد الذهاب إلى الإعدادات؟",
            AppLanguage.PT to "Para receber seus lembretes no horário, ative as notificações. Quer ir para as configurações?",
            AppLanguage.FR to "Pour recevoir vos rappels à temps, veuillez activer les notifications. Aller dans les paramètres ?"
        ),
        "notification_dialog_go" to mapOf(
            AppLanguage.ES to "Ir a configuración",
            AppLanguage.EN to "Go to settings",
            AppLanguage.HI to "सेटिंग्स पर जाएं",
            AppLanguage.ZH to "前往设置",
            AppLanguage.AR to "الذهاب إلى الإعدادات",
            AppLanguage.PT to "Ir para configurações",
            AppLanguage.FR to "Aller aux paramètres"
        ),
        "notification_dialog_not_now" to mapOf(
            AppLanguage.ES to "Ahora no",
            AppLanguage.EN to "Not now",
            AppLanguage.HI to "अभी नहीं",
            AppLanguage.ZH to "稍后",
            AppLanguage.AR to "ليس الآن",
            AppLanguage.PT to "Agora não",
            AppLanguage.FR to "Pas maintenant"
        ),
        "delete_all_subtitle" to mapOf(
            AppLanguage.ES to "Borra todos tus datos de la aplicación",
            AppLanguage.EN to "Delete all your app data",
            AppLanguage.HI to "आपके सभी ऐप डेटा को हटा देता है",
            AppLanguage.ZH to "删除您的所有应用数据",
            AppLanguage.AR to "حذف جميع بيانات التطبيق الخاصة بك",
            AppLanguage.PT to "Apaga todos os seus dados do aplicativo",
            AppLanguage.FR to "Supprime toutes les données de votre application"
        ),
        "reset_dialog_title" to mapOf(
            AppLanguage.ES to "¿Restablecer Aplicación?",
            AppLanguage.EN to "Reset Application?",
            AppLanguage.HI to "एप्लिकेशन रीसेट करें?",
            AppLanguage.ZH to "重置应用？",
            AppLanguage.AR to "إعادة تعيين التطبيق؟",
            AppLanguage.PT to "Restabelecer Aplicativo?",
            AppLanguage.FR to "Réinitialiser l'application ?"
        ),
        "delete_confirm_button" to mapOf(
            AppLanguage.ES to "BORRAR TODO",
            AppLanguage.EN to "DELETE ALL",
            AppLanguage.HI to "सभी हटाएं",
            AppLanguage.ZH to "删除全部",
            AppLanguage.AR to "مسح الكل",
            AppLanguage.PT to "APAGAR TUDO",
            AppLanguage.FR to "TOUT SUPPRIMER"
        ),
        "your_space_title" to mapOf(
            AppLanguage.ES to "TU ESPACIO",
            AppLanguage.EN to "YOUR SPACE",
            AppLanguage.HI to "आपका स्थान",
            AppLanguage.ZH to "您的空间",
            AppLanguage.AR to "مساحتك",
            AppLanguage.PT to "SEU ESPAÇO",
            AppLanguage.FR to "VOTRE ESPACE"
        ),
        "maintenance_title" to mapOf(
            AppLanguage.ES to "MANTENIMIENTO",
            AppLanguage.EN to "MAINTENANCE",
            AppLanguage.HI to "रखरखाव",
            AppLanguage.ZH to "维护",
            AppLanguage.AR to "الصيانة",
            AppLanguage.PT to "MANUTENÇÃO",
            AppLanguage.FR to "MAINTENANCE"
        ),
        "experience_title" to mapOf(
            AppLanguage.ES to "EXPERIENCIA",
            AppLanguage.EN to "EXPERIENCE",
            AppLanguage.HI to "अनुभव",
            AppLanguage.ZH to "体验",
            AppLanguage.AR to "تجربة",
            AppLanguage.PT to "EXPERIÊNCIA",
            AppLanguage.FR to "EXPÉRIENCE"
        ),
        "streak_label" to mapOf(
            AppLanguage.ES to "Racha",
            AppLanguage.EN to "Streak",
            AppLanguage.HI to "सिलसिला",
            AppLanguage.ZH to "连续",
            AppLanguage.AR to "سلسلة",
            AppLanguage.PT to "Sequência",
            AppLanguage.FR to "Séquence"
        ),
        "max_streak_short" to mapOf(
            AppLanguage.ES to "Máx: %d d",
            AppLanguage.EN to "Max: %d d",
            AppLanguage.HI to "अधिकतम: %d द",
            AppLanguage.ZH to "最高: %d 天",
            AppLanguage.AR to "الأقصى: %d ي",
            AppLanguage.PT to "Máx: %d d",
            AppLanguage.FR to "Max : %d j"
        ),
        "streak_days" to mapOf(
            AppLanguage.ES to "días",
            AppLanguage.EN to "days",
            AppLanguage.HI to "दिन",
            AppLanguage.ZH to "天",
            AppLanguage.AR to "أيام",
            AppLanguage.PT to "dias",
            AppLanguage.FR to "jours"
        ),
        "streak_explanation" to mapOf(
            AppLanguage.ES to "La racha cuenta los días consecutivos en los que completas al menos un recordatorio activo. Los días sin recordatorios programados se saltan automáticamente; solo los días con recordatorios pendientes pueden romper la racha si no los completas.",
            AppLanguage.EN to "Your streak counts consecutive days where you complete at least one active reminder. Days without scheduled reminders are automatically skipped; only days with pending reminders can break the streak if left incomplete.",
            AppLanguage.HI to "आपकी सिलसिला उन लगातार दिनों को गिनता है जब आप कम से कम एक सक्रिय अनुस्मारक पूरा करते हैं। बिना अनुस्मारक वाले दिन स्वचालित रूप से छोड़ दिए जाते हैं; केवल लंबित अनुस्मारक वाले दिन ही सिलसिला तोड़ सकते हैं।",
            AppLanguage.ZH to "您的连续记录统计的是您完成至少一个活跃提醒的连续天数。没有安排提醒的日子会自动跳过；只有有待办提醒的日子如果未完成才会中断连续记录。",
            AppLanguage.AR to "تحسب السلسلة الأيام المتتالية التي تكمل فيها تذكيرًا نشطًا واحدًا على الأقل. يتم تخطي الأيام التي لا تحتوي على تذكيرات مجدولة تلقائيًا؛ فقط الأيام التي تحتوي على تذكيرات معلقة يمكنها كسر السلسلة إذا تركت غير مكتملة.",
            AppLanguage.PT to "Sua sequência conta os dias consecutivos em que você completa pelo menos um lembrete ativo. Dias sem lembretes agendados são pulados automaticamente; apenas dias com lembretes pendentes podem quebrar a sequência se não forem concluídos.",
            AppLanguage.FR to "Votre séquence compte les jours consécutifs où vous complétez au moins un rappel actif. Les jours sans rappels programmés sont automatiquement ignorés ; seuls les jours avec des rappels en attente peuvent briser la séquence s'ils ne sont pas complétés."
        ),
        "streak_ended" to mapOf(
            AppLanguage.ES to "Racha terminada. ¡A empezar de nuevo con calma!",
            AppLanguage.EN to "Streak ended. Time to start fresh, with calm!",
            AppLanguage.HI to "सिलसिला टूट गया। शांति से फिर शुरू करें!",
            AppLanguage.ZH to "连续记录已结束。平静地重新开始吧！",
            AppLanguage.AR to "انتهت السلسلة. لنبدأ من جديد بهدوء!",
            AppLanguage.PT to "Sequência encerrada. Hora de recomeçar com calma!",
            AppLanguage.FR to "Séquence terminée. Il est temps de recommencer en douceur !"
        ),
        "few_data" to mapOf(
            AppLanguage.ES to "Pocos datos",
            AppLanguage.EN to "Not enough data",
            AppLanguage.HI to "कम डेटा",
            AppLanguage.ZH to "数据不足",
            AppLanguage.AR to "قليل من البيانات",
            AppLanguage.PT to "Poucos dados",
            AppLanguage.FR to "Peu de données"
        ),
        "activities_title" to mapOf(
            AppLanguage.ES to "ÚLTIMAS ACTIVIDADES",
            AppLanguage.EN to "LATEST ACTIVITIES",
            AppLanguage.HI to "अंतिम क्रियाएँ",
            AppLanguage.ZH to "最近活动",
            AppLanguage.AR to "آخر الأنشطة",
            AppLanguage.PT to "ÚLTIMAS ATIVIDADES",
            AppLanguage.FR to "DERNIÈRES ACTIVITÉS"
        ),
        "history_detail_title" to mapOf(
            AppLanguage.ES to "HISTORIAL DETALLADO",
            AppLanguage.EN to "DETAILED HISTORY",
            AppLanguage.HI to "विस्तृत इतिहास",
            AppLanguage.ZH to "详细历史",
            AppLanguage.AR to "السجل التفصيلي",
            AppLanguage.PT to "HISTÓRICO DETALHADO",
            AppLanguage.FR to "HISTORIQUE DÉTAILLÉ"
        ),
        "completed" to mapOf(
            AppLanguage.ES to "Completado",
            AppLanguage.EN to "Completed",
            AppLanguage.HI to "पूर्ण",
            AppLanguage.ZH to "已完成",
            AppLanguage.AR to "مكتمل",
            AppLanguage.PT to "Concluído",
            AppLanguage.FR to "Terminé"
        ),
        "postponed" to mapOf(
            AppLanguage.ES to "Pospuesto",
            AppLanguage.EN to "Postponed",
            AppLanguage.HI to "स्थगित",
            AppLanguage.ZH to "已推迟",
            AppLanguage.AR to "مؤجل",
            AppLanguage.PT to "Adiado",
            AppLanguage.FR to "Reporté"
        ),
        "name_intention" to mapOf(
            AppLanguage.ES to "Nombra tu intención",
            AppLanguage.EN to "Name your intention",
            AppLanguage.HI to "अपना इरादा नाम दें",
            AppLanguage.ZH to "命名您的意图",
            AppLanguage.AR to "سمّي نيتك",
            AppLanguage.PT to "Nomeie sua intenção",
            AppLanguage.FR to "Nommez votre intention"
        ),
        "notes_intention" to mapOf(
            AppLanguage.ES to "Notas e intenciones",
            AppLanguage.EN to "Notes and intentions",
            AppLanguage.HI to "नोट्स और इरादे",
            AppLanguage.ZH to "备注和意图",
            AppLanguage.AR to "ملاحظات ونوايا",
            AppLanguage.PT to "Notas e intenções",
            AppLanguage.FR to "Notes et intentions"
        ),
        "define_time" to mapOf(
            AppLanguage.ES to "Define la Hora",
            AppLanguage.EN to "Set Time",
            AppLanguage.HI to "समय सेट करें",
            AppLanguage.ZH to "设定时间",
            AppLanguage.AR to "حدد الوقت",
            AppLanguage.PT to "Definir Horário",
            AppLanguage.FR to "Définir l'Heure"
        ),
        "define_hour" to mapOf(
            AppLanguage.ES to "Define la Hora",
            AppLanguage.EN to "Set Time",
            AppLanguage.HI to "समय सेट करें",
            AppLanguage.ZH to "设定时间",
            AppLanguage.AR to "حدد الوقت",
            AppLanguage.PT to "Definir Horário",
            AppLanguage.FR to "Définir l'Heure"
        ),
        "hour" to mapOf(
            AppLanguage.ES to "Hora",
            AppLanguage.EN to "Hour",
            AppLanguage.HI to "घंटा",
            AppLanguage.ZH to "时",
            AppLanguage.AR to "ساعة",
            AppLanguage.PT to "Hora",
            AppLanguage.FR to "Heure"
        ),
        "minute" to mapOf(
            AppLanguage.ES to "Minutos",
            AppLanguage.EN to "Minutes",
            AppLanguage.HI to "मिनट",
            AppLanguage.ZH to "分",
            AppLanguage.AR to "دقيقة",
            AppLanguage.PT to "Minutos",
            AppLanguage.FR to "Minutes"
        ),
        "choose_date" to mapOf(
            AppLanguage.ES to "Elegir Fecha",
            AppLanguage.EN to "Choose Date",
            AppLanguage.HI to "तारीख चुनें",
            AppLanguage.ZH to "选择日期",
            AppLanguage.AR to "اختر التاريخ",
            AppLanguage.PT to "Escolher Data",
            AppLanguage.FR to "Choisir la Date"
        ),
        "selected_days" to mapOf(
            AppLanguage.ES to "Días seleccionados",
            AppLanguage.EN to "Selected days",
            AppLanguage.HI to "चयनित दिन",
            AppLanguage.ZH to "选定日期",
            AppLanguage.AR to "الأيام المختارة",
            AppLanguage.PT to "Dias selecionados",
            AppLanguage.FR to "Jours sélectionnés"
        ),
        "date_format" to mapOf(
            AppLanguage.ES to "Fecha: %s",
            AppLanguage.EN to "Date: %s",
            AppLanguage.HI to "तारीख: %s",
            AppLanguage.ZH to "日期: %s",
            AppLanguage.AR to "التاريخ: %s",
            AppLanguage.PT to "Data: %s",
            AppLanguage.FR to "Date : %s"
        ),
        "delete" to mapOf(
            AppLanguage.ES to "Eliminar",
            AppLanguage.EN to "Delete",
            AppLanguage.HI to "हटाएं",
            AppLanguage.ZH to "删除",
            AppLanguage.AR to "حذف",
            AppLanguage.PT to "Excluir",
            AppLanguage.FR to "Supprimer"
        ),
        "close" to mapOf(
            AppLanguage.ES to "Cerrar",
            AppLanguage.EN to "Close",
            AppLanguage.HI to "बंद करें",
            AppLanguage.ZH to "关闭",
            AppLanguage.AR to "إغلاق",
            AppLanguage.PT to "Fechar",
            AppLanguage.FR to "Fermer"
        ),
        "view_icons" to mapOf(
            AppLanguage.ES to "Ver 62 Iconos",
            AppLanguage.EN to "View 62 Icons",
            AppLanguage.HI to "62 आइकन देखें",
            AppLanguage.ZH to "查看 62 图标",
            AppLanguage.AR to "عرض 62 أيقونة",
            AppLanguage.PT to "Ver 62 Ícones",
            AppLanguage.FR to "Voir 62 Icônes"
        ),
        "search_icon" to mapOf(
            AppLanguage.ES to "Buscar icono...",
            AppLanguage.EN to "Search icon...",
            AppLanguage.HI to "आइकन खोजें...",
            AppLanguage.ZH to "搜索图标...",
            AppLanguage.AR to "ابحث عن الأيقونة...",
            AppLanguage.PT to "Pesquisar ícone...",
            AppLanguage.FR to "Rechercher une icône..."
        ),
        "total_completions" to mapOf(
            AppLanguage.ES to "Completaciones",
            AppLanguage.EN to "Completions",
            AppLanguage.HI to "पूर्ण कार्य",
            AppLanguage.ZH to "完成次数",
            AppLanguage.AR to "المرات المكتملة",
            AppLanguage.PT to "Conclusões",
            AppLanguage.FR to "Réalisations"
        ),
        "widget_no_reminders" to mapOf(
            AppLanguage.ES to "Sin intenciones hoy. Añade una.",
            AppLanguage.EN to "No intentions today. Add one.",
            AppLanguage.HI to "आज कोई इरादा नहीं। एक जोड़ें।",
            AppLanguage.ZH to "今天没有意图。添加一个。",
            AppLanguage.AR to "لا توجد نوايا اليوم. أضف واحدة.",
            AppLanguage.PT to "Sem intenções hoje. Adicione uma.",
            AppLanguage.FR to "Aucune intention aujourd'hui. Ajoutez-en une."
        ),
        "widget_title_reminder" to mapOf(
            AppLanguage.ES to "A TIEMPO • PRÓXIMA INTENCIÓN",
            AppLanguage.EN to "A TIEMPO • NEXT INTENTION",
            AppLanguage.HI to "A TIEMPO • अगला इरादा",
            AppLanguage.ZH to "A TIEMPO • 下一个意图",
            AppLanguage.AR to "A TIEMPO • النية التالية",
            AppLanguage.PT to "A TIEMPO • PRÓXIMA INTENÇÃO",
            AppLanguage.FR to "A TIEMPO • PROCHAINE INTENTION"
        ),
        "widget_title_stats" to mapOf(
            AppLanguage.ES to "A TIEMPO • RITMO PREMIUM",
            AppLanguage.EN to "A TIEMPO • PREMIUM RHYTHM",
            AppLanguage.HI to "A TIEMPO • प्रीमियम लय",
            AppLanguage.ZH to "A TIEMPO • 高级节奏",
            AppLanguage.AR to "A TIEMPO • إيقاع متميز",
            AppLanguage.PT to "A TIEMPO • RITMO PREMIUM",
            AppLanguage.FR to "A TIEMPO • RYTHME PREMIUM"
        ),
        "widget_label_active" to mapOf(
            AppLanguage.ES to "ACTIVOS",
            AppLanguage.EN to "ACTIVE",
            AppLanguage.HI to "सक्रिय",
            AppLanguage.ZH to "活跃",
            AppLanguage.AR to "نشط",
            AppLanguage.PT to "ATIVOS",
            AppLanguage.FR to "ACTIFS"
        ),
        "widget_label_urgent" to mapOf(
            AppLanguage.ES to "URGENTES",
            AppLanguage.EN to "URGENT",
            AppLanguage.HI to "तत्काल",
            AppLanguage.ZH to "紧急",
            AppLanguage.AR to "عاجل",
            AppLanguage.PT to "URGENTES",
            AppLanguage.FR to "URGENTS"
        ),
        "widget_label_pending" to mapOf(
            AppLanguage.ES to "PENDIENTES",
            AppLanguage.EN to "PENDING",
            AppLanguage.HI to "लंबित",
            AppLanguage.ZH to "待办",
            AppLanguage.AR to "معلق",
            AppLanguage.PT to "PENDENTES",
            AppLanguage.FR to "EN ATTENTE"
        ),
        "widget_fallback_name" to mapOf(
            AppLanguage.ES to "Sin recordatorios activos",
            AppLanguage.EN to "No active reminders",
            AppLanguage.HI to "कोई सक्रिय अनुस्मारक नहीं",
            AppLanguage.ZH to "没有活跃的提醒",
            AppLanguage.AR to "لا تذكيرات نشطة",
            AppLanguage.PT to "Sem lembretes ativos",
            AppLanguage.FR to "Aucun rappel actif"
        ),
        "widget_fallback_sub" to mapOf(
            AppLanguage.ES to "Pulsa para añadir uno",
            AppLanguage.EN to "Tap to add one",
            AppLanguage.HI to "एक जोड़ने के लिए दबाएँ",
            AppLanguage.ZH to "点击添加一个",
            AppLanguage.AR to "اضغط لإضافة واحدة",
            AppLanguage.PT to "Toque para adicionar um",
            AppLanguage.FR to "Appuyez pour en ajouter un"
        ),
        "journal_title" to mapOf(
            AppLanguage.ES to "Diario de Intenciones",
            AppLanguage.EN to "Intentions Journal",
            AppLanguage.HI to "इरादों की डायरी",
            AppLanguage.ZH to "意图日记",
            AppLanguage.AR to "مذكرات النوايا",
            AppLanguage.PT to "Diário de Intenções",
            AppLanguage.FR to "Journal des Intentions"
        ),
        "journal_today" to mapOf(
            AppLanguage.ES to "¿Cómo te sientes hoy con tus intenciones?",
            AppLanguage.EN to "How do you feel about your intentions today?",
            AppLanguage.HI to "आज अपने इरादों के बारे में कैसा महसूस करते हैं?",
            AppLanguage.ZH to "你今天对你的意图感觉如何？",
            AppLanguage.AR to "كيف تشعر تجاه نواياك اليوم؟",
            AppLanguage.PT to "Como você se sente hoje com suas intenções?",
            AppLanguage.FR to "Comment vous sentez-vous aujourd'hui avec vos intentions ?"
        ),
        "journal_placeholder" to mapOf(
            AppLanguage.ES to "Escribe libremente...",
            AppLanguage.EN to "Write freely...",
            AppLanguage.HI to "स्वतंत्र रूप से लिखें...",
            AppLanguage.ZH to "自由书写...",
            AppLanguage.AR to "اكتب بحرية...",
            AppLanguage.PT to "Escreva livremente...",
            AppLanguage.FR to "Écrivez librement..."
        ),
        "journal_save" to mapOf(
            AppLanguage.ES to "Guardar",
            AppLanguage.EN to "Save",
            AppLanguage.HI to "सहेजें",
            AppLanguage.ZH to "保存",
            AppLanguage.AR to "حفظ",
            AppLanguage.PT to "Salvar",
            AppLanguage.FR to "Enregistrer"
        ),
        "journal_empty" to mapOf(
            AppLanguage.ES to "Aún no has escrito nada.\nTus reflexiones vivirán aquí, sin presión.",
            AppLanguage.EN to "Nothing written yet.\nYour reflections will live here, pressure-free.",
            AppLanguage.HI to "अभी तक कुछ नहीं लिखा।\nआपके विचार यहाँ रहेंगे, बिना किसी दबाव के।",
            AppLanguage.ZH to "还没有写下任何内容。\n您的反思将保存在这里，毫无压力。",
            AppLanguage.AR to "لم تكتب شيئًا بعد.\nتأملاتك ستعيش هنا، بدون ضغط.",
            AppLanguage.PT to "Você ainda não escreveu nada.\nSuas reflexões viverão aqui, sem pressão.",
            AppLanguage.FR to "Vous n'avez encore rien écrit.\nVos réflexions vivront ici, sans pression."
        ),
        "journal_delete_title" to mapOf(
            AppLanguage.ES to "Eliminar entrada",
            AppLanguage.EN to "Delete entry",
            AppLanguage.HI to "प्रविष्टि हटाएं",
            AppLanguage.ZH to "删除记录",
            AppLanguage.AR to "حذف الإدخال",
            AppLanguage.PT to "Excluir entrada",
            AppLanguage.FR to "Supprimer l'entrée"
        ),
        "journal_delete_desc" to mapOf(
            AppLanguage.ES to "¿Quieres borrar esta reflexión? No se puede recuperar.",
            AppLanguage.EN to "Delete this reflection? It cannot be recovered.",
            AppLanguage.HI to "क्या आप यह प्रविष्टि हटाना चाहते हैं? इसे वापस नहीं लाया जा सकता।",
            AppLanguage.ZH to "要删除此记录吗？此操作无法恢复。",
            AppLanguage.AR to "هل تريد حذف هذا التأمل؟ لا يمكن استعادته.",
            AppLanguage.PT to "Deseja apagar esta reflexão? Não é possível recuperá-la.",
            AppLanguage.FR to "Voulez-vous supprimer cette réflexion ? Elle ne peut pas être récupérée."
        ),
        "journal_subtitle" to mapOf(
            AppLanguage.ES to "Reflexiona sin juicios ni métricas",
            AppLanguage.EN to "Reflect without judgment or metrics",
            AppLanguage.HI to "बिना निर्णय या मीट्रिक के प्रतिबिंबित करें",
            AppLanguage.ZH to "在没有评判或指标的情况下反思",
            AppLanguage.AR to "تأمل دون أحكام أو مقاييس",
            AppLanguage.PT to "Reflita sem julgamentos ou métricas",
            AppLanguage.FR to "Réfléchissez sans jugement ni mesure"
        ),
        "journal_entries_count" to mapOf(
            AppLanguage.ES to "entradas en total",
            AppLanguage.EN to "total entries",
            AppLanguage.HI to "कुल प्रविष्टियाँ",
            AppLanguage.ZH to "总条目",
            AppLanguage.AR to "إجمالي الإدخالات",
            AppLanguage.PT to "entradas no total",
            AppLanguage.FR to "entrées au total"
        ),
        "journal_today_count" to mapOf(
            AppLanguage.ES to "hoy",
            AppLanguage.EN to "today",
            AppLanguage.HI to "आज",
            AppLanguage.ZH to "今天",
            AppLanguage.AR to "اليوم",
            AppLanguage.PT to "hoje",
            AppLanguage.FR to "aujourd'hui"
        ),
        "journal_today_section" to mapOf(
            AppLanguage.ES to "HOY",
            AppLanguage.EN to "TODAY",
            AppLanguage.HI to "आज",
            AppLanguage.ZH to "今天",
            AppLanguage.AR to "اليوم",
            AppLanguage.PT to "HOJE",
            AppLanguage.FR to "AUJOURD'HUI"
        ),
        "journal_past_section" to mapOf(
            AppLanguage.ES to "ANTERIORES",
            AppLanguage.EN to "PAST ENTRIES",
            AppLanguage.HI to "पिछली प्रविष्टियाँ",
            AppLanguage.ZH to "过往记录",
            AppLanguage.AR to "الإدخالات السابقة",
            AppLanguage.PT to "ANTERIORES",
            AppLanguage.FR to "ENTRÉES ANTÉRIEURES"
        ),
        "journal_edit_entry" to mapOf(
            AppLanguage.ES to "Editar entrada",
            AppLanguage.EN to "Edit entry",
            AppLanguage.HI to "प्रविष्टि संपादित करें",
            AppLanguage.ZH to "编辑记录",
            AppLanguage.AR to "تعديل الإدخال",
            AppLanguage.PT to "Editar entrada",
            AppLanguage.FR to "Modifier l'entrée"
        ),
        "journal_save_edit" to mapOf(
            AppLanguage.ES to "Guardar cambios",
            AppLanguage.EN to "Save changes",
            AppLanguage.HI to "परिवर्तन सहेजें",
            AppLanguage.ZH to "保存更改",
            AppLanguage.AR to "حفظ التغييرات",
            AppLanguage.PT to "Salvar alterações",
            AppLanguage.FR to "Enregistrer les modifications"
        ),
        "delete_reminder_title" to mapOf(
            AppLanguage.ES to "Eliminar intención",
            AppLanguage.EN to "Delete intention",
            AppLanguage.HI to "इरादा हटाएं",
            AppLanguage.ZH to "删除意图",
            AppLanguage.AR to "حذف النية",
            AppLanguage.PT to "Excluir intenção",
            AppLanguage.FR to "Supprimer l'intention"
        ),
        "delete_reminder_desc" to mapOf(
            AppLanguage.ES to "¿Quieres borrar \"%s\"? No se puede deshacer.",
            AppLanguage.EN to "Delete \"%s\"? This cannot be undone.",
            AppLanguage.HI to "\"%s\" को हटाना है? यह कार्य पूर्ववत नहीं किया जा सकता।",
            AppLanguage.ZH to "删除 \"%s\" 吗？此操作无法撤销。",
            AppLanguage.AR to "حذف \"%s\"؟ لا يمكن التراجع عن هذا الإجراء.",
            AppLanguage.PT to "Deseja apagar \"%s\"? Não é possível desfazer.",
            AppLanguage.FR to "Voulez-vous supprimer « %s » ? Cette action est irréversible."
        ),
        "gentle_5min" to mapOf(
            AppLanguage.ES to "Respira. Tu intención te espera con calma.",
            AppLanguage.EN to "Breathe. Your intention awaits you with calm.",
            AppLanguage.HI to "गहरी साँस लें। आपका इरादा शांति से आपका इंतजार कर रहा है।",
            AppLanguage.ZH to "深呼吸。你的意图在平静中等待着你。",
            AppLanguage.AR to "تنفس. نيتك تنتظرك بهدوء.",
            AppLanguage.PT to "Respire. Sua intenção espera por você com calma.",
            AppLanguage.FR to "Respirez. Votre intention vous attend avec calme."
        ),
        "gentle_20min" to mapOf(
            AppLanguage.ES to "Diez minutos para cumplir tu intención. Sin prisa, pero sin pausa.",
            AppLanguage.EN to "Ten minutes left to honor your intention. No rush, but steady.",
            AppLanguage.HI to "अपने इरादे को पूरा करने के लिए दस मिनट शेष। जल्दी नहीं, लेकिन स्थिर रहें।",
            AppLanguage.ZH to "还剩十分钟履行你的意图。不急不缓。",
            AppLanguage.AR to "عشر دقائق متبقية لتحقيق نيتك. لا عجلة، ولكن بثبات.",
            AppLanguage.PT to "Dez minutos para cumprir sua intenção. Sem pressa, mas sem pausa.",
            AppLanguage.FR to "Dix minutes pour honorer votre intention. Sans précipitation, mais sans pause."
        ),
        "overdue_title" to mapOf(
            AppLanguage.ES to "%s — Atrasado",
            AppLanguage.EN to "%s — Overdue",
            AppLanguage.HI to "%s — विलंबित",
            AppLanguage.ZH to "%s — 已逾期",
            AppLanguage.AR to "%s — متأخر",
            AppLanguage.PT to "%s — Atrasado",
            AppLanguage.FR to "%s — En retard"
        ),
        "overdue_body" to mapOf(
            AppLanguage.ES to "El tiempo para cumplir este recordatorio expiró. La racha comienza de nuevo, sin culpa.",
            AppLanguage.EN to "Time for this reminder has passed. The streak starts anew, without guilt.",
            AppLanguage.HI to "इस अनुस्मारक का समय समाप्त हो गया। सिलसिला फिर से शुरू होता है, बिना किसी अपराधबोध के।",
            AppLanguage.ZH to "此提醒的时间已过。连续记录重新开始，无需自责。",
            AppLanguage.AR to "انتهى وقت هذا التذكير. السلسلة تبدأ من جديد، دون ذنب.",
            AppLanguage.PT to "O tempo para cumprir este lembrete expirou. A sequência começa de novo, sem culpa.",
            AppLanguage.FR to "Le temps pour honorer ce rappel est écoulé. La séquence recommence, sans culpabilité."
        ),
        "notif_chan_main_name" to mapOf(
            AppLanguage.ES to "Recordatorios A Tiempo",
            AppLanguage.EN to "A Tiempo Reminders",
            AppLanguage.HI to "A Tiempo अनुस्मारक",
            AppLanguage.ZH to "A Tiempo 提醒",
            AppLanguage.AR to "تذكيرات A Tiempo",
            AppLanguage.PT to "Lembretes A Tiempo",
            AppLanguage.FR to "Rappels A Tiempo"
        ),
        "notif_chan_main_desc" to mapOf(
            AppLanguage.ES to "Canal principal de recordatorios",
            AppLanguage.EN to "Main reminder channel",
            AppLanguage.HI to "मुख्य अनुस्मारक चैनल",
            AppLanguage.ZH to "主提醒频道",
            AppLanguage.AR to "قناة التذكير الرئيسية",
            AppLanguage.PT to "Canal principal de lembretes",
            AppLanguage.FR to "Canal principal de rappels"
        ),
        "notif_chan_gentle_name" to mapOf(
            AppLanguage.ES to "Recordatorios suaves",
            AppLanguage.EN to "Gentle reminders",
            AppLanguage.HI to "हल्के अनुस्मारक",
            AppLanguage.ZH to "温和提醒",
            AppLanguage.AR to "تذكيرات لطيفة",
            AppLanguage.PT to "Lembretes suaves",
            AppLanguage.FR to "Rappels doux"
        ),
        "notif_chan_gentle_desc" to mapOf(
            AppLanguage.ES to "Alertas sin sonido ni vibración",
            AppLanguage.EN to "Alerts without sound or vibration",
            AppLanguage.HI to "बिना ध्वनि या कंपन के सूचनाएं",
            AppLanguage.ZH to "无声音或振动的提醒",
            AppLanguage.AR to "تنبيهات بدون صوت أو اهتزاز",
            AppLanguage.PT to "Avisos sem som nem vibração",
            AppLanguage.FR to "Alertes sans son ni vibration"
        ),
        "time_past_error" to mapOf(
            AppLanguage.ES to "La hora seleccionada ya pasó. Elige una hora futura.",
            AppLanguage.EN to "The selected time has already passed. Choose a future time.",
            AppLanguage.HI to "चयनित समय बीत चुका है। कृपया भविष्य का समय चुनें।",
            AppLanguage.ZH to "所选时间已过。请选择未来的时间。",
            AppLanguage.AR to "الوقت المحدد قد مضى. اختر وقتًا مستقبليًا.",
            AppLanguage.PT to "O horário selecionado já passou. Escolha um horário futuro.",
            AppLanguage.FR to "L'heure sélectionnée est déjà passée. Choisissez une heure future."
        )
    )

    fun getString(key: String, lang: AppLanguage): String {
        return strings[key]?.get(lang) ?: strings[key]?.get(AppLanguage.ES) ?: key
    }

    fun getFormattedString(key: String, lang: AppLanguage, vararg args: Any): String {
        val template = getString(key, lang)
        return String.format(template, *args)
    }

    fun getLocale(lang: AppLanguage): Locale {
        return when (lang) {
            AppLanguage.ES -> Locale("es")
            AppLanguage.EN -> Locale("en")
            AppLanguage.HI -> Locale("hi")
            AppLanguage.ZH -> Locale("zh")
            AppLanguage.AR -> Locale("ar")
            AppLanguage.PT -> Locale("pt")
            AppLanguage.FR -> Locale("fr")
        }
    }

    // 100 quotes in Spanish
    val quotesEs = listOf(
        "Respira hondo y enfócate en el momento presente.",
        "Tu valor no se mide por tu productividad de hoy.",
        "Cada pequeño paso te acerca más a tu paz interior.",
        "La consistencia tranquila vence a la prisa caótica.",
        "Estás exactamente donde necesitas estar para crecer.",
        "Acepta lo que es, deja ir lo que fue, ten fe en lo que vendrá.",
        "Haz de la calma una prioridad en tu día de hoy.",
        "Sé amable contigo mismo; estás haciendo tu mejor esfuerzo.",
        "Un recordatorio de que tu bienestar físico es tu base.",
        "Hoy es un lienzo en blanco para sembrar intenciones claras.",
        "Desconecta para volver a conectar con tu interior.",
        "Agradece tres cosas simples que te rodean en este momento.",
        "El éxito consiste en mantener el rumbo con compasión.",
        "Un respiro de diez segundos puede cambiar tu perspectiva.",
        "No dejes que el ruido de los demás apague tu voz interior.",
        "En la sencillez de los pequeños hábitos reside el cambio.",
        "Cultiva pensamientos que te den paz, no ansiedad.",
        "Tu cuerpo es tu hogar; cuídalo, muévelo, dale descanso.",
        "El agua cura, camina si puedes, medita si lo necesitas.",
        "La paciencia es el espacio entre la reacción y la acción.",
        "Hoy decides actuar con propósito, no por inercia.",
        "Todo lo que buscas ya reside de alguna forma en ti.",
        "No pasa nada por pausar. La pausa también es progreso.",
        "La atención plena transforma lo ordinario en extraordinario.",
        "Abraza la imperfección; ahí reside el verdadero aprendizaje.",
        "Cada día trae consigo una nueva oportunidad de sanar.",
        "Pon tu energía en lo que sí puedes controlar hoy.",
        "Camina despacio, respira despacio, vive a tu propio ritmo.",
        "Agradecer abre las puertas de la abundancia silenciosa.",
        "Una mente en calma toma decisiones con claridad cristalina.",
        "El descanso es parte del trabajo, no un premio al final.",
        "Sintoniza con los latidos de tu corazón por un momento.",
        "Permítete sentir sin juzgar las emociones que surjan.",
        "Tu intención de hoy es tu brújula para los momentos difíciles.",
        "Sonríe, no por obligación, sino por liberar espacio interno.",
        "El silencio es el lenguaje donde se revela el alma.",
        "No compitas con nadie más que con tu yo del pasado.",
        "Protege tu paz mental por encima de cualquier urgencia.",
        "El presente es el único momento donde puedes sembrar.",
        "Disfruta del camino, no solo de la meta final.",
        "La suavidad interior es la mayor de las fortalezas.",
        "Libérate de las expectativas ajenas y camina libre.",
        "Cuida tus palabras: las que dices y las que te dices.",
        "La salud mental es tan importante como la física.",
        "Una taza de té, un libro, un suspiro: momentos sagrados.",
        "Encuentra la magia en la rutina diaria.",
        "Cada hábito positivo es un voto por la persona que quieres ser.",
        "La meditación es el arte de no hacer nada conscientemente.",
        "Sé el observador silencioso de tus propios pensamientos.",
        "La vida se despliega momento a momento, no la apresures.",
        "Mira el cielo hoy y recuerda lo inmenso que es el mundo.",
        "Un corazón agradecido es un imán para los milagros diarios.",
        "Confía en tu intuición; te conoce mejor que nadie.",
        "La respiración consciente es un ancla en medio de la tormenta.",
        "Deja ir la necesidad de complacer a todo el mundo.",
        "Invierte tiempo en lo que enciende una chispa en tu alma.",
        "La claridad viene de la acción calmada, no de la parálisis.",
        "Aprende a decir no para poder decirte sí a ti mismo.",
        "El orden exterior ayuda a crear tranquilidad en el interior.",
        "Una caminata de cinco minutos puede aclarar tus ideas.",
        "Eres fuerte, eres capaz, eres suficiente tal y como eres.",
        "El perdón es un regalo de libertad que te das a ti mismo.",
        "La alegría no está en las cosas, está en nosotros.",
        "Rodéate de personas que nutran tu bienestar emocional.",
        "Hoy es un buen día para soltar el control sobre el futuro.",
        "Cada respiración es una oportunidad para empezar de nuevo.",
        "Tus límites son sagrados; respétalos siempre.",
        "El amor propio es el inicio de un romance de por vida.",
        "Mantén tus pies en la tierra y tus intenciones elevadas.",
        "La naturaleza cura: busca un árbol, una flor o el viento.",
        "La creatividad florece en el espacio de la mente ociosa.",
        "Valora el silencio tanto como valoras la conversación.",
        "Un paso a la vez. No necesitas resolver toda tu vida hoy.",
        "Tu energía es tu posesión más preciada; canalízala bien.",
        "Observa cómo la luz cambia a lo largo de este hermoso día.",
        "Cierra los ojos, cuenta hasta cinco y deja ir la tensión.",
        "La disciplina consciente es el puente hacia la autorrealización.",
        "No te dejes arrastrar por las tormentas de la mente.",
        "El sol siempre vuelve a salir, incluso detrás de las nubes.",
        "Dedica hoy unos minutos a hacer absolutamente nada.",
        "El autocuidado no es egoísmo, es preservación de tu luz.",
        "Acepta tus sombras para poder brillar con mayor plenitud.",
        "Camina como si estuvieras besando la tierra con tus pies.",
        "El verdadero viaje de descubrimiento consiste en mirar con nuevos ojos.",
        "Una pequeña victoria por la mañana define el tono del día.",
        "Busca la paz en lugar de buscar tener la razón.",
        "La gratitud es la memoria del corazón contento.",
        "Haz una pausa para estirar tu cuerpo y liberar tensiones.",
        "Cada racha es un testimonio de tu amor propio y constancia.",
        "Sé el cambio que deseas experimentar en tu propio día.",
        "No dejes que los pendientes oscurezcan tus bendiciones.",
        "La calma no es la ausencia de caos, sino la paz en su interior.",
        "Eres el creador de tu propia atmósfera mental.",
        "Confía en el proceso silencioso de tu propio crecimiento.",
        "Un corazón ligero camina más lejos y con menos cansancio.",
        "La sabiduría comienza en la autorreflexión silenciosa.",
        "Sonríe al espejo hoy y reconoce el hermoso ser que hay ahí.",
        "Regálate el permiso de desconectar del mundo digital.",
        "Deja que cada hábito sea un acto de devoción a tu salud.",
        "Termina el día agradeciendo que diste lo mejor de ti."
    )

    // 100 quotes in English
    val quotesEn = listOf(
        "Take a deep breath and focus on the present moment.",
        "Your worth is not measured by your productivity today.",
        "Every small step brings you closer to your inner peace.",
        "Quiet consistency beats chaotic haste anytime.",
        "You are exactly where you need to be to grow.",
        "Accept what is, let go of what was, have faith in what will be.",
        "Make calm a priority in your day today.",
        "Be gentle with yourself; you are doing your absolute best.",
        "A reminder that your physical well-being is your foundation.",
        "Today is a blank canvas to sow clear intentions.",
        "Disconnect to reconnect with your inner self.",
        "Appreciate three simple things around you right now.",
        "Success is keeping your course with compassion.",
        "A ten-second pause can change your entire perspective.",
        "Don't let the noise of others drown out your inner voice.",
        "In the simplicity of small habits lies true change.",
        "Cultivate thoughts that give you peace, not anxiety.",
        "Your body is your home; care for it, move it, rest it.",
        "Water heals, walk if you can, meditate if you need to.",
        "Patience is the space between reaction and response.",
        "Today, choose to act with purpose, not out of inertia.",
        "Everything you seek already resides within you.",
        "It is okay to pause. Pausing is also progress.",
        "Mindfulness transforms the ordinary into the extraordinary.",
        "Embrace imperfection; that is where true learning lives.",
        "Each day brings with it a fresh opportunity to heal.",
        "Pour your energy into what you can control today.",
        "Walk slowly, breathe gently, live at your own pace.",
        "Gratitude opens the doors to silent abundance.",
        "A calm mind makes decisions with crystal clarity.",
        "Rest is part of the work, not just a reward at the end.",
        "Tune in to the beating of your heart for a moment.",
        "Allow yourself to feel without judging your emotions.",
        "Your intention today is your anchor in challenging times.",
        "Smile, not out of obligation, but to free inner space.",
        "Silence is the language where the soul reveals itself.",
        "Compete with no one else but your past self.",
        "Protect your peace of mind above any external urgency.",
        "The present is the only moment you can actively plant.",
        "Enjoy the journey, not just the final destination.",
        "Inner gentleness is the greatest strength of all.",
        "Release yourself from others' expectations and walk free.",
        "Watch your words: both what you say and what you think.",
        "Mental health is just as important as physical health.",
        "A cup of tea, a book, a deep sigh: sacred simple moments.",
        "Find the magic hidden in your daily routine.",
        "Every positive habit is a vote for the person you want to become.",
        "Meditation is the art of consciously doing nothing.",
        "Be the silent observer of your own thoughts.",
        "Life unfolds moment by moment, do not rush it.",
        "Look at the sky today and remember how vast the world is.",
        "A grateful heart is a magnet for daily miracles.",
        "Trust your intuition; it knows you better than anyone.",
        "Conscious breathing is an anchor in the middle of a storm.",
        "Let go of the constant need to please everyone.",
        "Invest time in what sets a spark in your soul.",
        "Clarity comes from calm action, not from paralysis.",
        "Learn to say no so you can say yes to yourself.",
        "Outer order helps create inner tranquility.",
        "A simple five-minute walk can clear your mind.",
        "You are strong, you are capable, you are enough as you are.",
        "Forgiveness is a gift of freedom you give to yourself.",
        "Joy is not in things, it is within us.",
        "Surround yourself with people who nurture your emotions.",
        "Today is a perfect day to release control of the future.",
        "Each breath is a brand-new chance to start over.",
        "Your boundaries are sacred; respect them always.",
        "Self-love is the beginning of a lifelong romance.",
        "Keep your feet on the ground and your intentions high.",
        "Nature heals: seek a tree, a flower, or the wind.",
        "Creativity blossoms in the space of an idle mind.",
        "Value silence as much as you value conversation.",
        "One step at a time. You don't need to figure out your whole life today.",
        "Your energy is your most precious possession; channel it well.",
        "Observe how the light shifts throughout this beautiful day.",
        "Close your eyes, count to five, and release tension.",
        "Mindful discipline is the bridge to self-realization.",
        "Do not let yourself be carried away by storms of the mind.",
        "The sun always rises again, even behind the clouds.",
        "Spend a few minutes today doing absolutely nothing.",
        "Self-care is not selfish, it is preserving your light.",
        "Accept your shadows so you can shine even brighter.",
        "Walk as if you were kissing the earth with your feet.",
        "The real voyage of discovery consists in seeing with new eyes.",
        "A small morning victory sets a peaceful tone for the day.",
        "Seek peace instead of seeking to be right.",
        "Gratitude is the memory of a happy heart.",
        "Pause to stretch your body and release physical tension.",
        "Each streak is a testament to your self-care and consistency.",
        "Be the change you want to experience in your own day.",
        "Don't let pending tasks overshadow your current blessings.",
        "Calm is not the absence of chaos, but peace within it.",
        "You are the creator of your own mental atmosphere.",
        "Trust the silent process of your own growth.",
        "A light heart walks further with less fatigue.",
        "Wisdom begins in quiet, honest self-reflection.",
        "Smile in the mirror today and recognize your beautiful being.",
        "Give yourself permission to unplug from the digital world.",
        "Let every habit be an act of devotion to your well-being.",
        "End the day grateful that you gave your best effort."
    )

    fun getWellnessQuote(lang: AppLanguage, dayOfYear: Int): String {
        val index = (dayOfYear - 1).coerceIn(0, 365) % 100
        return if (lang == AppLanguage.ES) {
            quotesEs[index]
        } else {
            quotesEn[index]
        }
    }

    fun getWellnessQuoteForToday(lang: AppLanguage): String {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_YEAR)
        return getWellnessQuote(lang, day)
    }
}
