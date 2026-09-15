
package paragraphstackexplorer;

/**
 *
 * @author Banele
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ParagraphStackExplorer extends JFrame {

    private JTextArea taParagraph;
    private JTextArea taResult;
    private JTextField txtWord;
    private JLabel lblStatus;

    private JButton btnLoad, btnDisplay, btnReverse;
    private JButton btnPush, btnSearch;
    private JButton btnPeek, btnPop, btnClear;

    private Stack<String> wordStack;

    public ParagraphStackExplorer()
    {
        setTitle("Paragraph Stack Explorer");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        wordStack = new Stack<>();

        // Main Panel Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Source Paragraph Section
        JLabel lblSource = new JLabel("Source paragraph");
        lblSource.setAlignmentX(Component.LEFT_ALIGNMENT);
        taParagraph = new JTextArea(5, 50);
        taParagraph.setLineWrap(true);
        taParagraph.setWrapStyleWord(true);
        taParagraph.setText("Stacks process values in last-in-first-out order. A paragraph can be cleaned into words, and every word can be pushed onto the stack. The final word becomes the top item.");

        // Top Control Buttons
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnLoad = new JButton("Load Paragraph");
        btnDisplay = new JButton("Display Stack");
        btnReverse = new JButton("Reverse Paragraph");
        row1.add(btnLoad);
        row1.add(btnDisplay);
        row1.add(btnReverse);

        // Word Input and Search/Push
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtWord = new JTextField(30);
        btnPush = new JButton("Push Word");
        btnSearch = new JButton("Search Position");
        row2.add(txtWord);
        row2.add(btnPush);
        row2.add(btnSearch);

        // Stack Direct Manipulation Buttons
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPeek = new JButton("Peek Top");
        btnPop = new JButton("Pop Top");
        btnClear = new JButton("Clear Stack");
        row3.add(btnPeek);
        row3.add(btnPop);
        row3.add(btnClear);

        // Result Visualization Section
        JLabel lblResult = new JLabel("Stack visualisation / result");
        lblResult.setAlignmentX(Component.LEFT_ALIGNMENT);
        taResult = new JTextArea(8, 50);
        taResult.setEditable(false);

        // Status bar
        lblStatus = new JLabel("Stack size: 0 | Top: None | Stack is empty", SwingConstants.CENTER);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to main layout
        mainPanel.add(lblSource);
        mainPanel.add(new JScrollPane(taParagraph));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(lblResult);
        mainPanel.add(new JScrollPane(taResult));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblStatus);

        add(mainPanel);

        // Register Action Listeners
        btnLoad.addActionListener(new BtnLoadClickListener());
        btnDisplay.addActionListener(new BtnDisplayClickListener());
        btnPush.addActionListener(new BtnPushClickListener());
        btnPeek.addActionListener(new BtnPeekClickListener());
        btnPop.addActionListener(new BtnPopClickListener());
        btnSearch.addActionListener(new BtnSearchClickListener());
        btnReverse.addActionListener(new BtnReverseClickListener());
        btnClear.addActionListener(new BtnClearClickListener());
    }

    private void updateStatus() 
    {
        if (wordStack.empty())
        {
            lblStatus.setText("Stack size: 0 | Top: None | Stack is empty");
        } 
        else
        {
            lblStatus.setText("Stack size: " + wordStack.size() + " | Top: " + wordStack.peek() + " | Stack is not empty");
        }
    }

    private class BtnLoadClickListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            wordStack.clear();
            String cleaned = taParagraph.getText()
                                        .toLowerCase()
                                        .replaceAll("[^a-z ]", " ")
                                        .replaceAll("\\s+", " ")
                                        .trim();

            if (cleaned.isEmpty()) 
            {
                lblStatus.setText("Enter a paragraph first.");
                return;
            }

            String[] words = cleaned.split("\\s+");
            
            for (String word : words) 
            {
                wordStack.push(word);
            }
            updateStatus();
        }
    }

    private class BtnPushClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String word = txtWord.getText()
                    .toLowerCase()
                    .replaceAll("[^a-z]", "")
                    .trim();

            if (word.isEmpty()) 
            {
                lblStatus.setText("Enter one valid word.");
                return;
            }

            wordStack.push(word);
            updateStatus();
        }
    }

    private class BtnPeekClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (wordStack.empty())
            {
                lblStatus.setText("Cannot peek: Stack is empty.");
            }
            else 
            {
                lblStatus.setText("Top word: " + wordStack.peek());
            }
        }
    }

    private class BtnPopClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (wordStack.empty()) 
            {
                lblStatus.setText("Cannot pop: Stack is empty.");
            } 
            else 
            {
                String removed = wordStack.pop();
                updateStatus();
                lblStatus.setText(removed + " was popped. " + lblStatus.getText());
            }
        }
    }

    private class BtnSearchClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String target = txtWord.getText()
                                    .toLowerCase()
                                    .replaceAll("[^a-z]", "")
                                    .trim();

            int position = wordStack.search(target);
            
            if (position == -1) 
            {
                lblStatus.setText(target + " was not found.");
            } 
            else
            {
                lblStatus.setText(target + " is position " + position + " from the top.");
            }
        }
    }

    private class BtnDisplayClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            taResult.setText("");
            Stack<String> copy = new Stack<>();
            copy.addAll(wordStack);

            int position = 1;
            while (!copy.empty()) {
                taResult.append(position + ". " + copy.pop() + "\n");
                position++;
            }
        }
    }

    private class BtnReverseClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            Stack<String> copy = new Stack<>();
            copy.addAll(wordStack);

            StringBuilder reversed = new StringBuilder();
            
            while (!copy.empty()) 
            {
                reversed.append(copy.pop()).append(" ");
            }
            taResult.setText(reversed.toString().trim());
        }
    }

    private class BtnClearClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            wordStack.clear();
            taResult.setText("");
            updateStatus();
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            new ParagraphStackExplorer().setVisible(true);
        });
    }
}