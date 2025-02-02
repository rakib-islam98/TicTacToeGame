package tictactoegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author iraki
 */
public class LevelSelection extends JPanel{
    
    private final Image imageBackground;
    
    public LevelSelection(ActionListener easyButtonAction,ActionListener mediumButtonAction,
            ActionListener hardButtonAction,ActionListener backButtonAction)
    {
        imageBackground=new ImageIcon(getClass().getResource("/images/LevelBackground.jpg")).getImage();
        
        JButton easyButton=new JButton(" Easy ");
        JButton mediumButton=new JButton(" Medium ");
        JButton hardButton=new JButton(" Hard ");
        
        styleButton(easyButton,mediumButton,hardButton);
        easyButton.setForeground(new Color(0,100,0));
        mediumButton.setForeground(Color.blue);
        hardButton.setForeground(Color.red);
        
        Image img=new ImageIcon(getClass().getResource("/images/BackButtonIcon.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton backButton=new JButton(new ImageIcon(img));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        
        easyButton.addActionListener(easyButtonAction);
        mediumButton.addActionListener(mediumButtonAction);
        hardButton.addActionListener(hardButtonAction);
        backButton.addActionListener(backButtonAction);
        
        JLabel titleLabel=new JLabel("SELECT LEVEL",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Curlz MT",Font.BOLD,30));
        titleLabel.setForeground(new Color(153,0,0));
        
        JPanel backPanel=new JPanel(new BorderLayout());
        backPanel.add(backButton,BorderLayout.EAST);
        backPanel.add(titleLabel,BorderLayout.SOUTH);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        backPanel.setOpaque(false);
        
        JPanel buttonPanel=new JPanel(new GridLayout(3,1,10,10));
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.setOpaque(false);
        
        JPanel ButtonPanelContainer=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets=new Insets(0,0,30,0);
        ButtonPanelContainer.add(buttonPanel,gbc);
        ButtonPanelContainer.setOpaque(false);
        
        setLayout(new BorderLayout());
        add(backPanel,BorderLayout.NORTH);
        add(ButtonPanelContainer,BorderLayout.SOUTH);
    }
    
    private void styleButton(JButton...buttons)
    {
        for(JButton button:buttons)
        {
            button.setFont(new Font("Curlz MT",Font.BOLD,25));
            button.setBackground(Color.orange);
            button.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
            button.setFocusPainted(false);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(imageBackground!=null)
        {
            Graphics2D g2d = (Graphics2D) g;

            // Enable high-quality image rendering
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the background image
            g2d.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
