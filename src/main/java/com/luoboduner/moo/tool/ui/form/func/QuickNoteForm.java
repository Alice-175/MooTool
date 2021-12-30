package com.luoboduner.moo.tool.ui.form.func;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.luoboduner.moo.tool.App;
import com.luoboduner.moo.tool.dao.TQuickNoteMapper;
import com.luoboduner.moo.tool.domain.TQuickNote;
import com.luoboduner.moo.tool.ui.UiConsts;
import com.luoboduner.moo.tool.ui.component.QuickNotePlainTextViewer;
import com.luoboduner.moo.tool.ui.component.QuickNotePlainTextViewerManager;
import com.luoboduner.moo.tool.ui.listener.func.QuickNoteListener;
import com.luoboduner.moo.tool.util.JTableUtil;
import com.luoboduner.moo.tool.util.MybatisUtil;
import com.luoboduner.moo.tool.util.UndoUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * <pre>
 * 随手记
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/8/15.
 */
@Getter
public class QuickNoteForm {
    private JPanel quickNotePanel;
    private JTable noteListTable;
    private JButton deleteButton;
    private JButton saveButton;
    private JSplitPane splitPane;
    private JButton addButton;
    private JComboBox fontNameComboBox;
    private JComboBox fontSizeComboBox;
    private JButton findButton;
    private JPanel deletePanel;
    private JScrollPane scrollPane;
    private JButton wrapButton;
    private JButton listItemButton;
    private JPanel rightPanel;
    private JPanel controlPanel;
    private JButton exportButton;
    private JTextField findTextField;
    private JButton doFindButton;
    private JButton doReplaceButton;
    private JTextField replaceTextField;
    private JPanel findReplacePanel;
    private JLabel findReplaceCloseLabel;
    private JCheckBox findMatchCaseCheckBox;
    private JCheckBox findWordsCheckBox;
    private JCheckBox findUseRegexCheckBox;
    private JPanel menuPanel;
    private JPanel findOptionPanel;
    private JSeparator findMenuSeparator;
    private JPanel findMenuSeparatorPanel;
    private JCheckBox trimBlankCheckBox;
    private JCheckBox clearTabTCheckBox;
    private JCheckBox clearEnterCheckBox;
    private JCheckBox scientificToNormalCheckBox;
    private JCheckBox toThousandthCheckBox;
    private JCheckBox enterToCommaCheckBox;
    private JCheckBox enterToCommaDoubleQuotesCheckBox;
    private JCheckBox commaToEnterCheckBox;
    private JButton startQuickReplaceButton;
    private JCheckBox tabToEnterCheckBox;
    private JCheckBox enterToCommaSingleQuotesCheckBox;
    private JScrollPane quickReplaceScrollPane;
    private JButton quickReplaceButton;
    private JLabel quickReplaceCloseLabel;
    private JSplitPane contentSplitPane;

    private static QuickNoteForm quickNoteForm;
    private static TQuickNoteMapper quickNoteMapper = MybatisUtil.getSqlSession().getMapper(TQuickNoteMapper.class);

    public static QuickNotePlainTextViewerManager quickNotePlainTextViewerManager;

    private QuickNoteForm() {
        UndoUtil.register(this);
    }

    public static QuickNoteForm getInstance() {
        if (quickNoteForm == null) {
            quickNoteForm = new QuickNoteForm();
        }
        return quickNoteForm;
    }

    public static void resetInstance() {
        quickNoteForm = null;
    }

    public static void init() {
        quickNoteForm = getInstance();

        quickNotePlainTextViewerManager = new QuickNotePlainTextViewerManager();

        initUi();

        initNoteListTable();

        initTextAreaFont();
    }

    private static void initUi() {
//        quickNoteForm.getRightPanel().removeAll();
//        if ("上方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getRightPanel().add(quickNoteForm.getControlPanel(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getRightPanel().add(quickNoteForm.getScrollPane(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
//        } else if ("下方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getRightPanel().add(quickNoteForm.getControlPanel(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getRightPanel().add(quickNoteForm.getScrollPane(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
//        }
//
//        quickNoteForm.getControlPanel().removeAll();
//        if ("上方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getControlPanel().add(quickNoteForm.getMenuPanel(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getControlPanel().add(quickNoteForm.getFindReplacePanel(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        } else if ("下方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getControlPanel().add(quickNoteForm.getMenuPanel(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getControlPanel().add(quickNoteForm.getFindReplacePanel(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        }
//
//        quickNoteForm.getFindReplacePanel().removeAll();
//        if ("上方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getFindReplacePanel().add(quickNoteForm.getFindMenuSeparatorPanel(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getFindReplacePanel().add(quickNoteForm.getFindOptionPanel(), new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        } else if ("下方".equals(App.config.getMenuBarPosition())) {
//            quickNoteForm.getFindReplacePanel().add(quickNoteForm.getFindOptionPanel(), new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//            quickNoteForm.getFindReplacePanel().add(quickNoteForm.getFindMenuSeparatorPanel(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        }
        quickNoteForm.getAddButton().setIcon(new FlatSVGIcon("icon/add.svg"));
        quickNoteForm.getSaveButton().setIcon(new FlatSVGIcon("icon/save.svg"));
        quickNoteForm.getFindButton().setIcon(new FlatSVGIcon("icon/find.svg"));
        quickNoteForm.getQuickReplaceButton().setIcon(new FlatSVGIcon("icon/replace.svg"));
        quickNoteForm.getDeleteButton().setIcon(new FlatSVGIcon("icon/remove.svg"));
        quickNoteForm.getExportButton().setIcon(new FlatSVGIcon("icon/export.svg"));
        quickNoteForm.getListItemButton().setIcon(new FlatSVGIcon("icon/list.svg"));
        quickNoteForm.getWrapButton().setIcon(new FlatSVGIcon("icon/wrap.svg"));

        quickNoteForm.getFindReplacePanel().setVisible(false);
        quickNoteForm.getQuickReplaceScrollPane().setVisible(false);

        quickNoteForm.getSplitPane().setDividerLocation((int) (App.mainFrame.getWidth() / 5));
        quickNoteForm.getNoteListTable().setRowHeight(UiConsts.TABLE_ROW_HEIGHT);

        quickNoteForm.getDeletePanel().setVisible(false);
        quickNoteForm.getQuickNotePanel().updateUI();

    }

    public static void initNoteListTable() {
        String[] headerNames = {"id", "名称"};
        DefaultTableModel model = new DefaultTableModel(null, headerNames);
        JTable noteListTable = quickNoteForm.getNoteListTable();
        noteListTable.setModel(model);
        // 隐藏表头
        JTableUtil.hideTableHeader(noteListTable);
        // 隐藏id列
        JTableUtil.hideColumn(noteListTable, 0);

        Object[] data;

        List<TQuickNote> quickNoteList = quickNoteMapper.selectAll();
        for (TQuickNote tQuickNote : quickNoteList) {
            data = new Object[2];
            data[0] = tQuickNote.getId();
            data[1] = tQuickNote.getName();
            model.addRow(data);
        }
        if (quickNoteList.size() > 0) {
            QuickNotePlainTextViewer plainTextViewer = quickNotePlainTextViewerManager.newPlainTextViewer(quickNoteList.get(0).getName());
            plainTextViewer.setText(quickNoteList.get(0).getContent());
            getInstance().getScrollPane().setViewportView(plainTextViewer);
            noteListTable.setRowSelectionInterval(0, 0);
            plainTextViewer.grabFocus();
            QuickNoteListener.selectedName = quickNoteList.get(0).getName();
        }
    }

    private static void initTextAreaFont() {
        String fontName = App.config.getQuickNoteFontName();
        int fontSize = App.config.getQuickNoteFontSize();
        if (fontSize == 0) {
            fontSize = quickNoteForm.getFindTextField().getFont().getSize() + 2;
        }

        getSysFontList();

        quickNoteForm.getFontNameComboBox().setSelectedItem(fontName);
        quickNoteForm.getFontSizeComboBox().setSelectedItem(String.valueOf(fontSize));
    }

    /**
     * 获取系统字体列表
     */
    private static void getSysFontList() {
        quickNoteForm.getFontNameComboBox().removeAllItems();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();
        for (String font : fonts) {
            if (StringUtils.isNotBlank(font)) {
                quickNoteForm.getFontNameComboBox().addItem(font);
            }
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        quickNotePanel = new JPanel();
        quickNotePanel.setLayout(new GridLayoutManager(1, 1, new Insets(12, 12, 12, 12), -1, -1));
        quickNotePanel.setMinimumSize(new Dimension(400, 300));
        quickNotePanel.setPreferredSize(new Dimension(400, 300));
        splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(281);
        splitPane.setDividerSize(10);
        quickNotePanel.add(splitPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setMinimumSize(new Dimension(0, 64));
        splitPane.setLeftComponent(panel1);
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(deletePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/icon/remove.png")));
        deleteButton.setText("");
        deleteButton.setToolTipText("删除");
        deletePanel.add(deleteButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        deletePanel.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        exportButton = new JButton();
        exportButton.setIcon(new ImageIcon(getClass().getResource("/icon/export_dark.png")));
        exportButton.setText("");
        exportButton.setToolTipText("导出");
        deletePanel.add(exportButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        noteListTable = new JTable();
        scrollPane1.setViewportView(noteListTable);
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane.setRightComponent(rightPanel);
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        rightPanel.add(controlPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayoutManager(1, 9, new Insets(0, 0, 0, 10), -1, -1));
        controlPanel.add(menuPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(getClass().getResource("/icon/menu-saveall_dark.png")));
        saveButton.setText("");
        saveButton.setToolTipText("保存(Ctrl+S)");
        menuPanel.add(saveButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        menuPanel.add(spacer2, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        fontNameComboBox = new JComboBox();
        fontNameComboBox.setToolTipText("设置字体");
        menuPanel.add(fontNameComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fontSizeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("5");
        defaultComboBoxModel1.addElement("6");
        defaultComboBoxModel1.addElement("7");
        defaultComboBoxModel1.addElement("8");
        defaultComboBoxModel1.addElement("9");
        defaultComboBoxModel1.addElement("10");
        defaultComboBoxModel1.addElement("11");
        defaultComboBoxModel1.addElement("12");
        defaultComboBoxModel1.addElement("13");
        defaultComboBoxModel1.addElement("14");
        defaultComboBoxModel1.addElement("15");
        defaultComboBoxModel1.addElement("16");
        defaultComboBoxModel1.addElement("17");
        defaultComboBoxModel1.addElement("18");
        defaultComboBoxModel1.addElement("19");
        defaultComboBoxModel1.addElement("20");
        defaultComboBoxModel1.addElement("21");
        defaultComboBoxModel1.addElement("22");
        defaultComboBoxModel1.addElement("23");
        defaultComboBoxModel1.addElement("24");
        defaultComboBoxModel1.addElement("25");
        defaultComboBoxModel1.addElement("26");
        defaultComboBoxModel1.addElement("27");
        defaultComboBoxModel1.addElement("28");
        defaultComboBoxModel1.addElement("29");
        defaultComboBoxModel1.addElement("30");
        fontSizeComboBox.setModel(defaultComboBoxModel1);
        fontSizeComboBox.setToolTipText("字号");
        menuPanel.add(fontSizeComboBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findButton = new JButton();
        findButton.setIcon(new ImageIcon(getClass().getResource("/icon/find_dark.png")));
        findButton.setText("");
        findButton.setToolTipText("查找(Ctrl+F)");
        menuPanel.add(findButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wrapButton = new JButton();
        wrapButton.setIcon(new ImageIcon(getClass().getResource("/icon/toggleSoftWrap_dark.png")));
        wrapButton.setText("");
        wrapButton.setToolTipText("自动换行");
        menuPanel.add(wrapButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listItemButton = new JButton();
        listItemButton.setIcon(new ImageIcon(getClass().getResource("/icon/listFiles_dark.png")));
        listItemButton.setText("");
        listItemButton.setToolTipText("显示/隐藏列表");
        menuPanel.add(listItemButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setIcon(new ImageIcon(getClass().getResource("/icon/add.png")));
        addButton.setText("");
        addButton.setToolTipText("新建(Ctrl+N)");
        menuPanel.add(addButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quickReplaceButton = new JButton();
        quickReplaceButton.setText("");
        menuPanel.add(quickReplaceButton, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findReplacePanel = new JPanel();
        findReplacePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        findReplacePanel.setVisible(true);
        controlPanel.add(findReplacePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        findOptionPanel = new JPanel();
        findOptionPanel.setLayout(new GridLayoutManager(2, 6, new Insets(0, 0, 0, 0), -1, -1));
        findReplacePanel.add(findOptionPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        findTextField = new JTextField();
        findOptionPanel.add(findTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        replaceTextField = new JTextField();
        findOptionPanel.add(replaceTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        findOptionPanel.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        findOptionPanel.add(spacer4, new GridConstraints(1, 3, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        doFindButton = new JButton();
        doFindButton.setText("查找");
        findOptionPanel.add(doFindButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        doReplaceButton = new JButton();
        doReplaceButton.setText("替换");
        findOptionPanel.add(doReplaceButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findReplaceCloseLabel = new JLabel();
        findReplaceCloseLabel.setIcon(new ImageIcon(getClass().getResource("/icon/remove_dark.png")));
        findReplaceCloseLabel.setText("");
        findOptionPanel.add(findReplaceCloseLabel, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findMatchCaseCheckBox = new JCheckBox();
        findMatchCaseCheckBox.setText("区分大小写");
        findOptionPanel.add(findMatchCaseCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findWordsCheckBox = new JCheckBox();
        findWordsCheckBox.setText("全词匹配");
        findOptionPanel.add(findWordsCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findUseRegexCheckBox = new JCheckBox();
        findUseRegexCheckBox.setText("使用正则");
        findOptionPanel.add(findUseRegexCheckBox, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findMenuSeparatorPanel = new JPanel();
        findMenuSeparatorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        findReplacePanel.add(findMenuSeparatorPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        findMenuSeparator = new JSeparator();
        findMenuSeparatorPanel.add(findMenuSeparator, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        controlPanel.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, true));
        contentSplitPane = new JSplitPane();
        contentSplitPane.setContinuousLayout(true);
        contentSplitPane.setDividerLocation(200);
        contentSplitPane.setDoubleBuffered(true);
        panel2.add(contentSplitPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        scrollPane = new JScrollPane();
        scrollPane.setMaximumSize(new Dimension(-1, -1));
        scrollPane.setMinimumSize(new Dimension(-1, -1));
        contentSplitPane.setLeftComponent(scrollPane);
        quickReplaceScrollPane = new JScrollPane();
        quickReplaceScrollPane.setMaximumSize(new Dimension(-1, -1));
        quickReplaceScrollPane.setMinimumSize(new Dimension(-1, -1));
        contentSplitPane.setRightComponent(quickReplaceScrollPane);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(17, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel3.setMaximumSize(new Dimension(-1, -1));
        panel3.setMinimumSize(new Dimension(-1, -1));
        quickReplaceScrollPane.setViewportView(panel3);
        trimBlankCheckBox = new JCheckBox();
        trimBlankCheckBox.setText("去掉空格");
        panel3.add(trimBlankCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel3.add(spacer5, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        clearTabTCheckBox = new JCheckBox();
        clearTabTCheckBox.setText("去掉Tab(\\t)");
        panel3.add(clearTabTCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scientificToNormalCheckBox = new JCheckBox();
        scientificToNormalCheckBox.setText("科学计数法 -> 普通数字");
        panel3.add(scientificToNormalCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        toThousandthCheckBox = new JCheckBox();
        toThousandthCheckBox.setText("普通数字 -> 千分位");
        panel3.add(toThousandthCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enterToCommaCheckBox = new JCheckBox();
        enterToCommaCheckBox.setText("换行(\\n) -> ,");
        panel3.add(enterToCommaCheckBox, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enterToCommaSingleQuotesCheckBox = new JCheckBox();
        enterToCommaSingleQuotesCheckBox.setText("换行(\\n) -> ','");
        panel3.add(enterToCommaSingleQuotesCheckBox, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enterToCommaDoubleQuotesCheckBox = new JCheckBox();
        enterToCommaDoubleQuotesCheckBox.setText("换行(\\n) -> \",\"");
        panel3.add(enterToCommaDoubleQuotesCheckBox, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        commaToEnterCheckBox = new JCheckBox();
        commaToEnterCheckBox.setText(", -> 换行");
        panel3.add(commaToEnterCheckBox, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabToEnterCheckBox = new JCheckBox();
        tabToEnterCheckBox.setText("Tab(\\t) -> 换行");
        panel3.add(tabToEnterCheckBox, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startQuickReplaceButton = new JButton();
        startQuickReplaceButton.setText("开始");
        panel3.add(startQuickReplaceButton, new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel3.add(separator1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        panel3.add(separator2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        final JSeparator separator3 = new JSeparator();
        panel3.add(separator3, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        clearEnterCheckBox = new JCheckBox();
        clearEnterCheckBox.setText("去掉换行");
        panel3.add(clearEnterCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        quickReplaceCloseLabel = new JLabel();
        quickReplaceCloseLabel.setIcon(new ImageIcon(getClass().getResource("/icon/remove_dark.png")));
        quickReplaceCloseLabel.setText("");
        panel4.add(quickReplaceCloseLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel4.add(spacer6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JSeparator separator4 = new JSeparator();
        panel3.add(separator4, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return quickNotePanel;
    }

}
