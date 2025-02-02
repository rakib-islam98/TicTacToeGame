package tictactoegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author iraki
 */
class MainMenu extends JPanel{
    
    private final Image backgroundImage;
    
    public MainMenu(ActionListener singlePlayerAction,ActionListener multiPlayerAction)
    {
        backgroundImage=new ImageIcon(getClass().getResource("/images/MainMenuBackground2.png")).getImage();
        
        JButton singlePlayer=new JButton(" Single Player ");
        JButton multiPlayer=new JButton(" Multi Player ");
        
        singlePlayer.setFont(new Font("Curlz MT",Font.BOLD,25));
        singlePlayer.setForeground(new Color(102,0,51));
        singlePlayer.setBackground(Color.orange);
        singlePlayer.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
        singlePlayer.setFocusPainted(false);
        
        multiPlayer.setFont(new Font("Curlz MT",Font.BOLD,25));
        multiPlayer.setForeground(Color.blue);
        multiPlayer.setBackground(Color.orange);
        multiPlayer.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
        multiPlayer.setFocusPainted(false);
        
        singlePlayer.addActionListener(singlePlayerAction);
        multiPlayer.addActionListener(multiPlayerAction);
        
        JPanel buttonPanel=new JPanel(new GridLayout(2,1,10,10));
        
        buttonPanel.add(singlePlayer);
        buttonPanel.add(multiPlayer);
        buttonPanel.setOpaque(false);
        
        JPanel ButtonPanelContainer=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets=new Insets(0,0,30,0);
        ButtonPanelContainer.add(buttonPanel,gbc);
        ButtonPanelContainer.setOpaque(false);
        
        setLayout(new BorderLayout());
        add(ButtonPanelContainer,BorderLayout.SOUTH);
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(backgroundImage!=null)
        {
            Graphics2D g2d = (Graphics2D) g;

            // Enable high-quality image rendering
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the background image
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
