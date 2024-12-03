package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class StoryIntroAndTransition extends JPanel {
    private Timer timer;
    private int index = 0; // Track the current part of the story
    private float alpha = 0.0f; // Fade effect alpha
    private boolean isFadingIn = true;
    private boolean isStoryFinished = false;
    private boolean transitionComplete = false;  // Transition complete state
    private String[] storyParts = {
        "Tôi là Đạt, một chàng trai từng được gọi là ‘con nhà người ta’ trong truyền thuyết. Cũng đúng thôi, tôi học giỏi, đẹp trai (đừng cười), lại còn ấp ủ giấc mơ trở thành sinh viên Đại học Bách Khoa – nơi toàn các thiên tài… hoặc là những người thích chịu cực hình.",
        
        "Ngày xưa, tôi sống ở một thị trấn nhỏ yên bình, nơi tôi có cô bạn thân từ thuở ấu thơ, Linh. Cô ấy là kiểu người mà ai cũng muốn làm bạn: tốt bụng, thông minh, nhưng đôi khi hơi ‘mặn’ hơn cả muối biển. Chúng tôi đã có rất nhiều kỷ niệm đẹp cùng nhau – từ việc học bài đến… nghịch phá nhà hàng xóm (chuyện này đừng kể cho ai biết nhé!).",
        
        "Nhưng tuổi trẻ mà, đâu phải lúc nào cũng khôn ngoan. Tôi bị lôi kéo vào hội đua xe trái phép – tưởng oai phong lẫm liệt, nhưng thực ra chỉ là lũ trẻ trâu đội mũ bảo hiểm giả.",
        
        "Đỉnh điểm là vào một đêm định mệnh, tôi đã gây tai nạn kinh hoàng...",
        
        "Người bị tông… lại chính là mẹ của Linh. Lúc đó, tôi hoảng loạn đến mức bỏ trốn mà không kịp nghĩ gì. Nói thật, nếu có giải thưởng ‘người chạy nhanh nhất thị trấn’, chắc tôi đoạt giải luôn.",
        
        "Sau đó, tôi bỏ lên thành phố. Tôi lao vào học hành, chơi game online để quên đi tất cả. Nhưng không, quá khứ như cái bumerang – ném đi bao nhiêu thì nó lại quay về đập vào mặt mình.",
        
        "Đêm nào tôi cũng mơ thấy mình bị quái vật truy đuổi, hoặc bị một con xe máy không có tài xế đuổi theo. Rồi mỗi khi giật mình tỉnh dậy, tôi chỉ biết tự nhủ: ‘Chắc tại bài tập OOP của thầy Hóa khó quá nên bị ác mộng thôi.",
        
        "Cứ thế, tôi từ một học sinh A+ OOP-trò cưng của thầy Hóa, trở thành ‘con nghiện cà phê và ác mộng’. Thế giới thực, tôi là người lủi thủi; trong thế giới ảo, tôi là ‘sát thủ đứng top server’. Nhưng sâu thẳm trong lòng, tôi biết mình đang chạy trốn – không chỉ khỏi lỗi lầm, mà còn khỏi chính bản thân mình.",
        
        "Và giờ thì đây, tôi đang đứng trước ngưỡng cửa quan trọng nhất của cuộc đời. Liệu tôi có đủ dũng cảm để đối mặt với Linh, người bạn thân mà tôi đã làm tổn thương nhiều năm trước? Hay tôi sẽ mãi mãi là chàng trai mắc kẹt trong vòng xoáy của tội lỗi và nỗi sợ hãi? Tôi không biết, nhưng nếu đây là một game, chắc chắn tôi sẽ chọn chế độ chơi dễ…",
        
        "Hãy theo dõi hành trình của tôi để xem liệu tôi có tìm được câu trả lời. Và nếu bạn nghĩ mình sẽ cười được khi nghe câu chuyện này, thì đừng cố nhịn...",
        
        "Vì nếu bạn cười!",
        
        "Tôi sẽ được 10 OOP"
    };

    private String displayedText = "";  // Text to be displayed
    private boolean isTextVisible = true;

    public StoryIntroAndTransition() {
        setPreferredSize(new Dimension(960, 540));
        setBackground(Color.BLACK);
        setOpaque(true);

        // Handle mouse click to proceed through the story
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isStoryFinished) {
                    transitionComplete = true;  // Mark transition complete
                } else {
                    // Move to the next part of the story
                    index++;
                    if (index == storyParts.length) {
                        isStoryFinished = true;
                    }
                    repaint();
                }
            }
        });

        startFadeEffect();
    }
    // Method to start the fade effect
    private void startFadeEffect() {
        SwingWorker<Void, Void> fadeWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (alpha < 1.0f && !isStoryFinished) {
                    Thread.sleep(50); // Wait for a bit to create the fade effect
                    alpha += 0.05f;
                    repaint();
                }

                // Once fading in is done, start displaying the story
                while (index < storyParts.length) {
                    Thread.sleep(3000);  // Wait for 3 seconds before moving to the next part of the story
                    index++;
                    repaint();
                }
                // Once the story is finished, fade out
                while (alpha > 0.0f) {
                    Thread.sleep(50); // Wait for a bit to create the fade effect in reverse
                    alpha -= 0.05f;
                    repaint();
                }
                transitionComplete = true;  // Mark the transition as complete
                return null;
            }

            @Override
            protected void done() {
                // Transition to the game when the story finishes
                if (transitionComplete) {
                    startGame();  // Start the game after the transition is complete
                }
            }
        };

        fadeWorker.execute();  // Start the fade effect
    }

    // Check if the transition is complete
    public boolean isTransitionComplete() {
        return transitionComplete;
    }

    // Method to draw the story screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw fade effect
        g2d.setColor(new Color(0, 0, 0, Math.min(alpha, 1.0f)));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the current part of the story
        if (index < storyParts.length) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Serif", Font.PLAIN, 18));

            String text = storyParts[index];
            FontMetrics fm = g2d.getFontMetrics();
            int lineHeight = fm.getHeight();  // Get the height of each line

            // Calculate the width of the screen and center it
            int x = 20;  // Set a small margin from the left edge
            int y = getHeight() / 2 - (lineHeight * (text.split("\n").length / 2));  // Position the text vertically centered

            String[] words = text.split(" ");  // Split the text into words
            StringBuilder currentLine = new StringBuilder();
            int lineY = y;

            // Iterate through each word to create the lines
            for (String word : words) {
                String tempLine = currentLine + word + " ";
                if (fm.stringWidth(tempLine) < getWidth() - 40) {  // Check if the line fits within the screen width
                    currentLine.append(word).append(" ");
                } else {
                    // Draw the current line and start a new one
                    g2d.drawString(currentLine.toString(), x, lineY);
                    currentLine = new StringBuilder(word + " "); // Start a new line
                    lineY += lineHeight;
                }
            }

            // Draw the last line if there is any
            if (currentLine.length() > 0) {
                g2d.drawString(currentLine.toString(), x, lineY);
            }
        }
    }

    // Method to start the game after the story is finished
    private void startGame() {
        // Transition to the game
        //JFrame gameFrame = new JFrame("Game Window");
        // gameFrame.setSize(960, 540);
        // gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // gameFrame.setLocationRelativeTo(null);
        // gameFrame.setVisible(true);

        // Close the story screen when transitioning to the game
        SwingUtilities.getWindowAncestor(this).dispose();
    }
}
