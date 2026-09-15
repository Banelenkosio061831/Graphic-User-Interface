
package uniquewordexplorer;

/**
 *
 * @author Banele
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class UniqueWordExplorer extends JFrame
{

    // Default sample text from slide
    private static final String DEFAULT_PARAGRAPH = 
        "Java collections help programmers organise data. Java sets store unique values, " +
        "while data cleaning prepares words for reliable comparison. A SET ignores duplicate " +
        "elements, but case and punctuation must be cleaned first!";

    // GUI Components
    private JTextArea taParagraph;
    private JTextArea taUniqueWords;
    private JTextField txtWord;
    private JTextField txtIgnoredWords;
    private JLabel lblStatus;

    private JButton btnProcess;
    private JButton btnDisplay;
    private JButton btnStatistics;
    private JButton btnContains;
    private JButton btnRemove;
    private JButton btnRemoveIgnored;
    private JButton btnFilterTechnical; // NEW BUTTON for Step 9
    private JButton btnClear;
    private JButton btnReload;

    // Set Data Structure
    private Set<String> uniqueWords;

    public UniqueWordExplorer()
    {
        setTitle("Unique Word Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 720);
        setLocationRelativeTo(null);

        // Step 1 & 2: Construct Set
        uniqueWords = new HashSet<>();

        // Root Panel Setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Source Paragraph Panel
        JPanel pnlSource = new JPanel(new BorderLayout());
        pnlSource.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Source paragraph", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12)));

        taParagraph = new JTextArea(DEFAULT_PARAGRAPH, 4, 50);
        taParagraph.setLineWrap(true);
        taParagraph.setWrapStyleWord(true);
        pnlSource.add(new JScrollPane(taParagraph), BorderLayout.CENTER);

        // 2. Main Process Action Buttons
        JPanel pnlProcessBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        btnProcess = new JButton("Process Paragraph");
        btnProcess.setBackground(new Color(255, 235, 156));
        btnDisplay = new JButton("Display Set");
        btnDisplay.setBackground(new Color(218, 238, 243));
        btnStatistics = new JButton("Show Statistics");
        btnStatistics.setBackground(new Color(218, 238, 243));
        
        // Step 9 UI Component Integration
        btnFilterTechnical = new JButton("Filter Technical Words");
        btnFilterTechnical.setBackground(new Color(218, 238, 243));

        pnlProcessBtns.add(btnProcess);
        pnlProcessBtns.add(btnDisplay);
        pnlProcessBtns.add(btnStatistics);
        pnlProcessBtns.add(btnFilterTechnical);

        // 3. Search and Remove Word Row
        JPanel pnlWordOps = new JPanel(new BorderLayout(5, 0));
        txtWord = new JTextField("Search or remove a word...");
        btnContains = new JButton("Contains?");
        btnContains.setBackground(new Color(218, 238, 243));
        btnRemove = new JButton("Remove");
        btnRemove.setBackground(new Color(252, 213, 213));

        JPanel pnlWordBtns = new JPanel(new GridLayout(1, 2, 5, 0));
        pnlWordBtns.add(btnContains);
        pnlWordBtns.add(btnRemove);

        pnlWordOps.add(txtWord, BorderLayout.CENTER);
        pnlWordOps.add(pnlWordBtns, BorderLayout.EAST);

        // 4. Ignored Words Row
        JPanel pnlIgnoredOps = new JPanel(new BorderLayout(5, 0));
        txtIgnoredWords = new JTextField("a,the,and,of,to");
        btnRemoveIgnored = new JButton("Remove Ignored Words");
        btnRemoveIgnored.setBackground(new Color(255, 235, 156));

        pnlIgnoredOps.add(txtIgnoredWords, BorderLayout.CENTER);
        pnlIgnoredOps.add(btnRemoveIgnored, BorderLayout.EAST);

        // 5. Unique Words Output Panel
        JPanel pnlUnique = new JPanel(new BorderLayout());
        pnlUnique.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Unique words", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12)));

        taUniqueWords = new JTextArea(6, 50);
        taUniqueWords.setEditable(false);
        pnlUnique.add(new JScrollPane(taUniqueWords), BorderLayout.CENTER);

        // 6. Clear and Reload Row
        JPanel pnlClearReload = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        btnClear = new JButton("Clear Set");
        btnClear.setBackground(new Color(252, 213, 213));
        btnReload = new JButton("Reload Paragraph");
        btnReload.setBackground(new Color(218, 238, 243));

        pnlClearReload.add(btnClear);
        pnlClearReload.add(btnReload);

        // 7. Status Label Footer
        lblStatus = new JLabel("Unique words: 0 | Set is empty", SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblStatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(8, 5, 8, 5)
        ));

        // Assemble Layout
        mainPanel.add(pnlSource);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(pnlProcessBtns);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(pnlWordOps);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(pnlIgnoredOps);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(pnlUnique);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(pnlClearReload);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(lblStatus);

        add(mainPanel);

        // Register Action Listeners
        btnProcess.addActionListener(new BtnProcessClickListener());
        btnDisplay.addActionListener(new BtnDisplayClickListener());
        btnContains.addActionListener(new BtnContainsClickListener());
        btnRemove.addActionListener(new BtnRemoveClickListener());
        btnRemoveIgnored.addActionListener(new BtnRemoveIgnoredClickListener());
        btnFilterTechnical.addActionListener(new BtnFilterTechnicalClickListener()); // Step 9 Listener
        btnStatistics.addActionListener(new BtnStatisticsClickListener());
        btnClear.addActionListener(new BtnClearClickListener());
        btnReload.addActionListener(e -> taParagraph.setText(DEFAULT_PARAGRAPH));
    }

    // Step 3 & 4: Process paragraph
    private class BtnProcessClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            uniqueWords.clear();
            String paragraph = taParagraph.getText();

            String cleanedText = paragraph
                    .toLowerCase()
                    .replaceAll("[^a-z ]", " ")
                    .trim();

            if (cleanedText.isEmpty()) 
            {
                lblStatus.setText("Enter a paragraph first.");
                return;
            }

            String[] words = cleanedText.split("\\s+");
            int accepted = 0;
            int duplicates = 0;

            for (String word : words) 
            {
                boolean changed = uniqueWords.add(word);
                if (changed) 
                {
                    accepted++;
                } 
                else
                {
                    duplicates++;
                }
            }

            lblStatus.setText("Unique words: " + uniqueWords.size() + " | Duplicates skipped: " + duplicates);
        }
    }

    // Step 5: Display Set
    private class BtnDisplayClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            taUniqueWords.setText("");

            for (String word : uniqueWords)
            {
                taUniqueWords.append(word + "\n");
            }
        }
    }

    // Step 6: Search with contains(...)
    private class BtnContainsClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String target = txtWord.getText()
                    .toLowerCase()
                    .replaceAll("[^a-z]", "")
                    .trim();

            if (target.isEmpty()) {
                lblStatus.setText("Enter one word to search.");
            } else if (uniqueWords.contains(target)) {
                lblStatus.setText(target + " is in the Set.");
            } else {
                lblStatus.setText(target + " is not in the Set.");
            }
        }
    }

    // Step 7: Remove target word
    private class BtnRemoveClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String target = txtWord.getText()
                    .toLowerCase()
                    .replaceAll("[^a-z]", "")
                    .trim();

            boolean removed = uniqueWords.remove(target);

            if (removed) {
                lblStatus.setText(target + " was removed.");
            } else {
                lblStatus.setText(target + " was not found.");
            }
        }
    }

    // Step 8: Bulk removal with removeAll(...)
    private class BtnRemoveIgnoredClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ignoredText = txtIgnoredWords.getText()
                    .toLowerCase()
                    .replaceAll("[^a-z,]", "");

            String[] ignoredArray = ignoredText.split(",");

            Set<String> ignoredWords = new HashSet<>();

            for (String ignored : ignoredArray) {
                if (!ignored.trim().isEmpty()) {
                    ignoredWords.add(ignored.trim());
                }
            }

            uniqueWords.removeAll(ignoredWords);

            lblStatus.setText("Ignored words removed. " + uniqueWords.size() + " words remain.");
        }
    }

    // STEP 9: Implementation from Step 9 image (addAll, containsAll, retainAll)
    private class BtnFilterTechnicalClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. Setup required words Set and check containsAll
            Set<String> requiredWords = new HashSet<>();
            requiredWords.add("java");
            requiredWords.add("data");

            boolean hasEveryRequiredWord = uniqueWords.containsAll(requiredWords);

            // 2. Define technical vocabulary Set
            Set<String> technicalWords = new HashSet<>();
            technicalWords.add("java");
            technicalWords.add("data");
            technicalWords.add("collections");
            technicalWords.add("set");

            // 3. Create a temporary copy set using addAll to protect the original Set
            Set<String> matches = new HashSet<>();
            matches.addAll(uniqueWords);

            // 4. Perform intersection using retainAll
            matches.retainAll(technicalWords);

            // 5. Display the matching technical words in the text area
            taUniqueWords.setText("--- Technical Matches ---\n");
            for (String match : matches) {
                taUniqueWords.append(match + "\n");
            }

            lblStatus.setText("Has required words (java, data)? " + hasEveryRequiredWord + 
                              " | Matches found: " + matches.size());
        }
    }

    // Step 10: Statistics with size() and isEmpty()
    private class BtnStatisticsClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (uniqueWords.isEmpty()) {
                lblStatus.setText("The Set is empty.");
            } else {
                lblStatus.setText("The Set contains " + uniqueWords.size() + " unique words.");
            }
        }
    }

    // Step 11: Clear Set
    private class BtnClearClickListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            uniqueWords.clear();
            taUniqueWords.setText("");
            lblStatus.setText("The Set has been cleared.");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->{new UniqueWordExplorer().setVisible(true);});
    }
}