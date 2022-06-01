package nl.windesheim.ictm2f.gui.order;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.order.Order;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.GridPoint;

public class OrderGUIPanel extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;

    private JTextArea inputNewOrder;
    private JButton createNewEmptyOrderButton, selectOrderButton, deleteOrderButton;
    private JList<String> orderSelect;
    private DefaultListModel<String> existingOrderList;

    public OrderGUIPanel(Dimension size, GUIThemes guiTheme) {
        this.screenDimension = size;
        this.guiTheme = guiTheme;
        this.setPreferredSize(size);
        this.setSize(size);
        this.setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());
        this.setLayout(null);

        this.existingOrderList = new DefaultListModel<String>();

        this.inputNewOrder = new JTextArea();
        this.createNewEmptyOrderButton = new JButton("Create Order");
        this.orderSelect = new JList<String>(existingOrderList);
        JScrollPane orderSelectScrollPane = new JScrollPane(this.orderSelect);
        this.selectOrderButton = new JButton("Select");
        this.deleteOrderButton = new JButton("Delete");

        this.inputNewOrder.setBounds(10, 45, (int) size.getWidth() - 180, 28);
        this.inputNewOrder.setFont(this.inputNewOrder.getFont().deriveFont(Font.PLAIN, 20));

        this.createNewEmptyOrderButton.setBounds((int) size.getWidth() - 160, 45, 150, 28);

        this.orderSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderSelectScrollPane.setBounds(10, 128, (int) size.getWidth() - 20, 200);

        this.selectOrderButton.setBounds(10, 350, (int) size.getWidth() / 2 - 20, 28);
        this.deleteOrderButton.setBounds((int) size.getWidth() / 2 + 10, 350, (int) size.getWidth() / 2 - 20, 28);

        this.add(this.inputNewOrder);
        this.add(this.createNewEmptyOrderButton);
        this.add(orderSelectScrollPane);
        this.add(this.selectOrderButton);
        this.add(this.deleteOrderButton);

        this.setupListeners();
    }

    private void setupListeners() {
        this.createNewEmptyOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputNewOrder.getText().length() == 0) {
                    return;
                }

                for (Order order : Main.getInstance().getOrderManager().getOrders()) {
                    if (order.getName().equalsIgnoreCase(inputNewOrder.getText())) {
                        return;
                    }
                }
                Main.getInstance().getOrderManager().createEmptyOrder(inputNewOrder.getText());
                inputNewOrder.setText("");

                Main.getInstance().getOrderManager().save();
                Main.getInstance().getDatabase().save(Main.getInstance().getCachedData().getData());

                repaint();
            }
        });

        this.selectOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) orderSelect.getSelectedValue();
                if (!selectedItem.contains(":")) {
                    return;
                }
                selectOrder(selectedItem.split(":")[0]);
                Main.getInstance().getOrderManager().save();
                Main.getInstance().getDatabase().save(Main.getInstance().getCachedData().getData());
                repaint();
            }
        });

        this.deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) orderSelect.getSelectedValue();
                if (!selectedItem.contains(":")) {
                    return;
                }
                deleteOrder(selectedItem.split(":")[0]);
                Main.getInstance().getOrderManager().save();
                Main.getInstance().getDatabase().save(Main.getInstance().getCachedData().getData());
                repaint();
            }
        });

        this.orderSelect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedItem = (String) orderSelect.getSelectedValue();
                    if (!selectedItem.contains(":")) {
                        return;
                    }
                    selectOrder(selectedItem.split(":")[0]);
                    Main.getInstance().getOrderManager().save();
                    Main.getInstance().getDatabase().save(Main.getInstance().getCachedData().getData());
                    repaint();
                }
            }
        });
    }

    protected void selectOrder(String orderName) {
        Main.getInstance().getOrderManager().setCurrentOrder(orderName);

        Main.getInstance().getSolver().clearPoints();
        Main.getInstance().getSolver().clearPath();
        for (GridPoint point : Main.getInstance().getOrderManager().getCurrentOrder().getPoints()) {
            Main.getInstance().getSolver().addPoint(point);
        }
        Main.getInstance().getSolver().SolveDynamic();
        Main.getInstance().getGuiManager().getControlPanel().repaint();
        Main.getInstance().getGuiManager().getStatusPanel().getOrderPanel().repaint();
    }

    protected void deleteOrder(String orderName) {
        Main.getInstance().getOrderManager().deleteOrder(orderName);

        Main.getInstance().getSolver().clearPoints();
        Main.getInstance().getSolver().clearPath();
        Main.getInstance().getGuiManager().getControlPanel().repaint();
        Main.getInstance().getGuiManager().getStatusPanel().getOrderPanel().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.existingOrderList.clear();
        for (Order order : Main.getInstance().getOrderManager().getOrders()) {
            this.existingOrderList.addElement(order.getName() + ": " + order.getPoints().size() + " points");
        }

        this.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.setForeground(this.guiTheme.getTheme().getTextColor());

        this.inputNewOrder.setBackground(this.guiTheme.getTheme().getAltBackgroundColor());
        this.inputNewOrder.setForeground(this.guiTheme.getTheme().getTextColor());
        this.createNewEmptyOrderButton.setBackground(this.guiTheme.getTheme().getAltBackgroundColor());
        this.createNewEmptyOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());
        this.orderSelect.setBackground(this.guiTheme.getTheme().getAltBackgroundColor());
        this.orderSelect.setForeground(this.guiTheme.getTheme().getTextColor());
        this.selectOrderButton.setBackground(this.guiTheme.getTheme().getAltBackgroundColor());
        this.selectOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());
        this.deleteOrderButton.setBackground(this.guiTheme.getTheme().getAltBackgroundColor());
        this.deleteOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.setFont(new Font("default", Font.PLAIN, 28));
        g.drawString("Create Order", (int) (this.screenDimension.getWidth() / 2
                - g.getFontMetrics(g.getFont()).stringWidth("Create Order") / 2), 30);
        g.drawString("Select Order", (int) (this.screenDimension.getWidth() / 2
                - g.getFontMetrics(g.getFont()).stringWidth("Select Order") / 2), 110);
    }
}
