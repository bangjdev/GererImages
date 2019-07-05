package gui;

import main.BrowseController;
import main.ExportController;
import main.ListListener;
import model.DataModel;
import model.ExcelHandler;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private ExcelHandler excel = new ExcelHandler();

    private JPanel pnlMain;
    private JPanel pnlButtons;
    private JButton btnBrowse = new JButton("Browse");
    private JButton btnExport = new JButton("Export");
    private DefaultListModel<DataModel> listImageData = new DefaultListModel<>();
    private JList<DataModel> listImage = new JList<>(listImageData);
    private JTextArea txtInfos = new JTextArea();
    private JLabel lblDirectory = new JLabel("List images");
    private JLabel lblInfos = new JLabel("Information");
    private BrowseController browseController = new BrowseController(listImageData);
    private ExportController exportController = new ExportController(listImageData, excel);
    private Thumbnail thumbnail = new Thumbnail();
    private ListListener listListener = new ListListener(listImageData, thumbnail, txtInfos);


    public MainGUI() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Images management");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocation(200, 200);

        setVisible(true);

        initComponents();

        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

//        test();

        initEventListeners();

    }

    private void initComponents() {
        pnlMain = new JPanel(new GridLayout(1, 2));
        JPanel pnlDirectory = new JPanel(new BorderLayout());
        JPanel pnlInfos = new JPanel(new BorderLayout());
        JPanel pnlInfoCenter = new JPanel(new GridLayout(2, 1));
        pnlButtons = new JPanel(new FlowLayout());

        pnlMain.add(pnlDirectory);
        pnlMain.add(pnlInfos);

        listImage.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane listScroller = new JScrollPane(listImage);

        JScrollPane infoScroller = new JScrollPane(txtInfos);
        txtInfos.setToolTipText("Information shown here");

        pnlDirectory.add(lblDirectory, BorderLayout.NORTH);
        pnlDirectory.add(listScroller, BorderLayout.CENTER);
        pnlInfos.add(lblInfos, BorderLayout.NORTH);
        pnlInfos.add(pnlInfoCenter, BorderLayout.CENTER);
        pnlInfoCenter.add(infoScroller);
        pnlInfoCenter.add(thumbnail);



        btnBrowse.setPreferredSize(new Dimension(100, 30));
        btnBrowse.setHorizontalTextPosition(JButton.CENTER);
        btnExport.setPreferredSize(new Dimension(100, 30));
        btnExport.setHorizontalTextPosition(JButton.CENTER);
        pnlButtons.add(btnBrowse);
        pnlButtons.add(btnExport);

        listImage.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof DataModel) {
                    ((JLabel) renderer).setText(((DataModel) value).getFileName());
                }
                return renderer;
            }
        });

    }

    private void initEventListeners() {

        btnBrowse.addActionListener(browseController);
        btnExport.addActionListener(exportController);
        listImage.addMouseListener(listListener);

    }
}
