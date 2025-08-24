
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class IslamicMoodApp extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);
    private String currentEmotion = "";
    private String currentCategory = "";

    private final JLabel optionsHeader = new JLabel("", SwingConstants.CENTER);
    private final JLabel contentHeader = new JLabel("", SwingConstants.CENTER);
    private final JTextArea contentArea = new JTextArea();

    private final Map<String, Map<String, String>> db = new HashMap<>();
    private final Map<String, String> emotionMessages = new HashMap<>();
    private Font parisienneFont;

    public IslamicMoodApp() {
        super("Islamic Mood App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 560);
        setLocationRelativeTo(null);

        loadFonts(); // load Parisienne dynamically
        buildEmotionMessages(); // load comforting messages
        buildDatabase(); // load Ayats/Hadiths/Stories
        buildPages(); // build UI pages

        setVisible(true);
    }

    private void loadFonts() {
        try {
            // Place "Parisienne-Regular.ttf" in your project folder
            parisienneFont = Font.createFont(Font.TRUETYPE_FONT, new File("Parisienne-Regular.ttf")).deriveFont(32f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(parisienneFont);
        } catch (Exception e) {
            e.printStackTrace();
            parisienneFont = new Font("Serif", Font.PLAIN, 32); // fallback
        }
    }

    private void buildEmotionMessages() {
        emotionMessages.put("Sad", "Do not be sad, for Allah is always with you 💖");
        emotionMessages.put("Stressed", "Do not be stressed; place your trust in Allah 🌸");
        emotionMessages.put("Grateful", "Keep being grateful, and your blessings will multiply 🌷");
        emotionMessages.put("Depressed", "Do not lose hope; Allah’s mercy is near ✨");
        emotionMessages.put("Anxious", "Be calm; Allah’s plan is perfect 🌿");
        emotionMessages.put("Angry", "Take a deep breath; patience is beloved to Allah 🌸");
        emotionMessages.put("Lonely", "You are never truly alone; Allah is always close 🌷");
        emotionMessages.put("Hopeful", "Keep hope alive; Allah guides the sincere ✨");
    }

    private void buildPages() {
        root.add(buildEmotionsPage(), "emotions");
        root.add(buildOptionsPage(), "options");
        root.add(buildContentPage(), "content");
        setContentPane(root);
        cards.show(root, "emotions");
    }

    private JPanel buildEmotionsPage() {
        JPanel page = new JPanel();
        page.setLayout(new BorderLayout(12, 12));
        page.setOpaque(true);
        page.setBackground(Color.decode("#FFE6EA"));
        page.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel welcome = new JLabel("Welcome to Islamic Mood App", SwingConstants.CENTER);
        welcome.setFont(parisienneFont);
        welcome.setForeground(new Color(105, 0, 105));
        welcome.setOpaque(false);

        JLabel subtitle = new JLabel("Find comfort through Qur'an, Hadith and Stories — choose how you're feeling", SwingConstants.CENTER);
        subtitle.setFont(parisienneFont.deriveFont(18f));
        subtitle.setForeground(new Color(105, 0, 105));
        subtitle.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(0, 2, 14, 14));
        grid.setOpaque(false);
        String[] emotions = {"Sad", "Stressed", "Grateful", "Depressed", "Anxious", "Angry", "Lonely", "Hopeful"};

        for (String emo : emotions) {
            JButton b = new JButton(emo);
            styleButton(b);
            addHoverEffect(b);
            b.addActionListener(e -> {
                currentEmotion = emo;
                optionsHeader.setText(emotionMessages.getOrDefault(emo, "Choose an option"));
                cards.show(root, "options");
            });
            grid.add(b);
        }

        page.add(welcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setOpaque(false);
        center.add(subtitle, BorderLayout.NORTH);
        center.add(grid, BorderLayout.CENTER);

        page.add(center, BorderLayout.CENTER);
        return page;
    }

    private JPanel buildOptionsPage() {
        JPanel page = new JPanel();
        page.setLayout(new BorderLayout(12, 12));
        page.setOpaque(true);
        page.setBackground(Color.decode("#FFE6EA"));
        page.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        optionsHeader.setFont(parisienneFont.deriveFont(24f));
        optionsHeader.setForeground(new Color(105, 0, 105));
        optionsHeader.setOpaque(false);
        page.add(optionsHeader, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 12));
        grid.setOpaque(false);

        String[] categories = {"Ayats", "Hadiths", "Stories"};
        for (String cat : categories) {
            JButton b = new JButton(cat);
            styleButton(b);
            addHoverEffect(b);
            b.addActionListener(e -> {
                currentCategory = cat;
                renderContent();
                cards.show(root, "content");
            });
            grid.add(b);
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        JButton back = new JButton("⬅ Back to Emotions");
        styleButton(back);
        addHoverEffect(back);
        back.addActionListener(e -> cards.show(root, "emotions"));
        bottom.add(back);

        page.add(grid, BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);
        return page;
    }

    private JPanel buildContentPage() {
        JPanel page = new JPanel();
        page.setLayout(new BorderLayout(12, 12));
        page.setOpaque(true);
        page.setBackground(Color.decode("#FFE6EA"));
        page.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        contentHeader.setFont(parisienneFont.deriveFont(24f));
        contentHeader.setForeground(new Color(105, 0, 105));
        contentHeader.setOpaque(false);
        page.add(contentHeader, BorderLayout.NORTH);

        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Scheherazade", Font.PLAIN, 18));
        contentArea.setForeground(new Color(50, 0, 50));
        contentArea.setBackground(new Color(255, 245, 247));
        contentArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#FFE6EA")),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        page.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottom.setOpaque(false);
        JButton back = new JButton("⬅ Back");
        styleButton(back);
        addHoverEffect(back);
        back.addActionListener(e -> cards.show(root, "options"));
        bottom.add(back);

        page.add(bottom, BorderLayout.SOUTH);
        return page;
    }

    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(Color.decode("#FFC0CB"));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setBorder(BorderFactory.createLineBorder(Color.decode("#FFE6EA"), 2, true));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
    }

    private void addHoverEffect(JButton b) {
        Color base = Color.decode("#FFC0CB");
        Color hover = Color.decode("#FFB6C1");
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                b.setBackground(hover);
            }
            public void mouseExited(MouseEvent evt) {
                b.setBackground(base);
            }
        });
    }

    private void renderContent() {
        contentHeader.setText(currentCategory + " — " + currentEmotion);
        Map<String, String> categories = db.getOrDefault(currentEmotion, Collections.emptyMap());
        String content = categories.getOrDefault(currentCategory, "Content coming soon, inshā'Allāh.");
        contentArea.setText(content);
        contentArea.setCaretPosition(0);
    }

    private void buildDatabase() {
        // Complete content for each emotion
        put("Sad", "Ayats", "إِنَّ مَعَ الْعُسْرِ يُسْرًا\nInna ma'a al-'usri yusra\nIndeed, with hardship comes ease. (Qur'an 94:5-6)");
        put("Sad", "Hadiths", "«لاَ يُصِيبُ الْمُؤْمِنَ مِنْ نَصَبٍ وَلاَ وَصَبٍ وَلاَ هَمٍّ وَلاَ حَزَنٍ...»\nThe Prophet ﷺ said: 'No fatigue, nor disease, nor sorrow, nor sadness, nor hurt, nor distress befalls a Muslim, even the prick of a thorn, but that Allah expiates some of his sins for that.' — Ṣaḥīḥ al-Bukhārī & Ṣaḥīḥ Muslim.");
        put("Sad", "Stories", "Prophet Ya'qub (AS) wept for Yusuf (AS) but said: 'I only complain of my suffering and my grief to Allah.' (Qur'an 12:86). His patience and turning to Allah illustrate how to handle deep sorrow.");

        put("Stressed", "Ayats", "الَّذِينَ آمَنُوا وَتَطْمَئِنُّ قُلُوبُهُمْ بِذِكْرِ اللَّهِ\nThose who believe and whose hearts find comfort in the remembrance of Allah. Indeed, in the remembrance of Allah do hearts find rest. (Qur'an 13:28)");
        put("Stressed", "Hadiths", "Hadith Qudsi: 'I am as My servant thinks of Me, and I am with him when he remembers Me.' — Ṣaḥīḥ al-Bukhārī & Ṣaḥīḥ Muslim.\nRemembrance and trust in Allah ease stress.");
        put("Stressed", "Stories", "During the Hijrah, in the cave, Abu Bakr (RA) felt fear; the Prophet ﷺ reassured him: 'Do not grieve; indeed Allah is with us.' (Qur'an 9:40)");

        put("Grateful", "Ayats", "وَإِذْ تَأَذَّنَ رَبُّكُمْ لَئِن شَكَرْتُمْ لَأَزِيدَنَّكُمْ\nAnd when your Lord proclaimed: 'If you are grateful, I will surely increase you [in favor].' (Qur'an 14:7)");
        put("Grateful", "Hadiths", "The Prophet ﷺ said: 'Should I not be a thankful servant?' (Ṣaḥīḥ al-Bukhārī & Ṣaḥīḥ Muslim) — demonstrating the Prophet's gratitude.");
        put("Grateful", "Stories", "The Prophet ﷺ often showed gratitude by offering long night prayers (as an expression of thankfulness).");

        put("Depressed", "Ayats", "لَا تَهِنُوا وَلَا تَحْزَنُوا وَأَنتُمُ الْأَعْلَوْنَ إِن كُنتُم مُّؤْمِنِينَ\nDo not lose hope nor be sad, and you will be superior if you are true believers. (Qur'an 3:139)");
        put("Depressed", "Hadiths", "The Prophet ﷺ said: 'Wondrous is the affair of the believer… if he is harmed, he is patient and that is good for him.' — Ṣaḥīḥ Muslim.");
        put("Depressed", "Stories", "Prophet Ayyub (AS) endured severe trials patiently; Allah restored his family and health due to his steadfastness (Qur'an 21:83–84).");

        put("Anxious", "Ayats", "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا\nAllah does not burden a soul beyond that it can bear. (Qur'an 2:286)");
        put("Anxious", "Hadiths", "The Prophet ﷺ said: 'If you were to rely upon Allah with the reliance He is due, He would provide for you as He provides for the birds...' — Jami' at-Tirmidhi.");
        put("Anxious", "Stories", "Yunus (AS) cried out in the darkness of the whale and turned to Allah sincerely; his sincere supplication brought relief and rescue (Qur'an 21:87).");

        put("Angry", "Ayats", "وَالْكَاظِمِينَ الْغَيْظَ وَالْعَافِينَ عَنِ النَّاسِ\nThose who restrain anger and pardon the people; Allah loves the doers of good. (Qur'an 3:134)");
        put("Angry", "Hadiths", "A man asked the Prophet ﷺ for advice; he said: 'Do not become angry.' — Ṣaḥīḥ al-Bukhārī.");
        put("Angry", "Stories", "At the conquest of Makkah the Prophet ﷺ forgave many who had harmed him previously—an example of restraint and mercy.");

        put("Lonely", "Ayats", "وَإِذَا سَأَلَكَ عِبَادِي عَنِّي فَإِنِّي قَرِيبٌ\nAnd when My servants ask you concerning Me — indeed I am near. (Qur'an 2:186)");
        put("Lonely", "Hadiths", "Allah says: 'I am as My servant thinks of Me; I am with him when he remembers Me.' — Ṣaḥīḥ al-Bukhārī & Ṣaḥīḥ Muslim.");
        put("Lonely", "Stories", "Maryam (AS) experienced isolation yet Allah comforted and provided for her in childbirth (Qur'an 19:23–26).");

        put("Hopeful", "Ayats", "قُلْ يَا عِبَادِيَ الَّذِينَ أَسْرَفُوا عَلَى أَنفُسِهِمْ لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ\nSay: 'O My servants who have transgressed against themselves, do not despair of the mercy of Allah.' (Qur'an 39:53)");
        put("Hopeful", "Hadiths", "The Prophet ﷺ said: 'Allah's mercy prevails over His wrath.' — authentic collections.");
        put("Hopeful", "Stories", "The story of Umar ibn al-Khattab (RA) — once opposed to Islam, later guided; a reminder that sincere turning to Allah opens the way to hope and transformation.");
    }

    private void put(String mood, String category, String content) {
        Map<String, String> map = db.computeIfAbsent(mood, k -> new HashMap<>());
        map.put(category, content);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IslamicMoodApp::new);
    }
}
