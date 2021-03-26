package com.luoboduner.moo.tool.ui.component;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.luoboduner.moo.tool.App;
import com.luoboduner.moo.tool.ui.Init;
import com.luoboduner.moo.tool.ui.dialog.AboutDialog;
import com.luoboduner.moo.tool.ui.dialog.KeyMapDialog;
import com.luoboduner.moo.tool.ui.dialog.SettingDialog;
import com.luoboduner.moo.tool.ui.dialog.SystemEnvResultDialog;
import com.luoboduner.moo.tool.ui.form.MainWindow;
import com.luoboduner.moo.tool.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * 顶部菜单栏
 */
public class TopMenuBar extends JMenuBar {
    private static final Log logger = LogFactory.get();

    private static TopMenuBar menuBar;

    private static JMenu themeMenu;

    private static JMenu fontFamilyMenu;

    private static JMenu fontSizeMenu;

    private static int initialThemeItemCount = -1;

    private static int initialFontFamilyItemCount = -1;

    private static int initialFontSizeItemCount = -1;

    private static String[] themeNames = {
            "Darcula",
            "BeautyEye",
            "系统默认",
            "Flat Light",
            "Flat IntelliJ",
            "Flat Dark",
            "Flat Darcula(推荐)",
            "Dark purple",
            "IntelliJ Cyan",
            "IntelliJ Light"};

    private static String[] fontNames = {
            "Microsoft YaHei",
            "Microsoft YaHei Light",
            "Microsoft YaHei UI",
            "Microsoft YaHei UI Light"};

    private static String[] fontSizes = {
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26"};

    private TopMenuBar() {
    }

    public static TopMenuBar getInstance() {
        if (menuBar == null) {
            menuBar = new TopMenuBar();
        }
        return menuBar;
    }

    public void init() {
        TopMenuBar topMenuBar = getInstance();
        // ---------应用
        JMenu appMenu = new JMenu();
        appMenu.setText("应用");
        // 退出
        JMenuItem exitMenuItem = new JMenuItem();
        exitMenuItem.setText("退出");
        exitMenuItem.addActionListener(e -> exitActionPerformed());
        appMenu.add(exitMenuItem);
        topMenuBar.add(appMenu);
        // ---------设置
        JMenu settingMenu = new JMenu();
        settingMenu.setText("设置");
        settingMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingActionPerformed();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        topMenuBar.add(settingMenu);
        // ---------外观
        JMenu appearanceMenu = new JMenu();
        appearanceMenu.setText("外观");

        JCheckBoxMenuItem defaultMaxWindowitem = new JCheckBoxMenuItem("默认最大化窗口");
        defaultMaxWindowitem.setSelected(App.config.isDefaultMaxWindow());
        defaultMaxWindowitem.addActionListener(e -> {
            boolean selected = defaultMaxWindowitem.isSelected();
            App.config.setDefaultMaxWindow(selected);
            App.config.save();
        });
        appearanceMenu.add(defaultMaxWindowitem);

        JCheckBoxMenuItem unifiedBackgrounditem = new JCheckBoxMenuItem("窗口颜色沉浸式");
        unifiedBackgrounditem.setSelected(App.config.isUnifiedBackground());
        unifiedBackgrounditem.addActionListener(e -> {
            boolean selected = unifiedBackgrounditem.isSelected();
            App.config.setUnifiedBackground(selected);
            App.config.save();
            UIManager.put("TitlePane.unifiedBackground", selected);
            FlatLaf.updateUI();
        });
        appearanceMenu.add(unifiedBackgrounditem);

        themeMenu = new JMenu();
        themeMenu.setText("主题风格");

        initThemesMenu();

        appearanceMenu.add(themeMenu);

        fontFamilyMenu = new JMenu();
        fontFamilyMenu.setText("字体");
        initFontFamilyMenu();

        appearanceMenu.add(fontFamilyMenu);

        fontSizeMenu = new JMenu();
        fontSizeMenu.setText("字号");
        initFontSizeMenu();

        appearanceMenu.add(fontSizeMenu);

        topMenuBar.add(appearanceMenu);
        // ---------快捷键
        JMenu keyMapMenu = new JMenu();
        keyMapMenu.setText("快捷键");
        keyMapMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                keyMapActionPerformed();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        topMenuBar.add(keyMapMenu);
        // ---------调试
        JMenu debugMenu = new JMenu();
        debugMenu.setText("调试");
        // 查看日志
        JMenuItem logMenuItem = new JMenuItem();
        logMenuItem.setText("查看日志");
        logMenuItem.addActionListener(e -> logActionPerformed());

        debugMenu.add(logMenuItem);
        // 系统环境变量
        JMenuItem sysEnvMenuItem = new JMenuItem();
        sysEnvMenuItem.setText("系统环境变量");
        sysEnvMenuItem.addActionListener(e -> sysEnvActionPerformed());

        debugMenu.add(sysEnvMenuItem);

        topMenuBar.add(debugMenu);
        // ---------关于
        JMenu aboutMenu = new JMenu();
        aboutMenu.setText("关于");
        aboutMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                aboutActionPerformed();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        topMenuBar.add(aboutMenu);
    }

    private void initFontSizeMenu() {

        if (initialFontSizeItemCount < 0)
            initialFontSizeItemCount = fontSizeMenu.getItemCount();
        else {
            // remove old items
            for (int i = fontSizeMenu.getItemCount() - 1; i >= initialFontSizeItemCount; i--)
                fontSizeMenu.remove(i);
        }
        for (String fontSize : fontSizes) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(fontSize);
            item.setSelected(fontSize.equals(String.valueOf(App.config.getFontSize())));
            item.addActionListener(this::fontSizeChanged);
            fontSizeMenu.add(item);
        }
    }


    private void initFontFamilyMenu() {

        if (initialFontFamilyItemCount < 0)
            initialFontFamilyItemCount = fontFamilyMenu.getItemCount();
        else {
            // remove old items
            for (int i = fontFamilyMenu.getItemCount() - 1; i >= initialFontFamilyItemCount; i--)
                fontFamilyMenu.remove(i);
        }
        for (String font : fontNames) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(font);
            item.setSelected(font.equals(App.config.getFont()));
            item.addActionListener(this::fontFamilyChanged);
            fontFamilyMenu.add(item);
        }
    }

    private void initThemesMenu() {

        if (initialThemeItemCount < 0)
            initialThemeItemCount = themeMenu.getItemCount();
        else {
            // remove old items
            for (int i = themeMenu.getItemCount() - 1; i >= initialThemeItemCount; i--)
                themeMenu.remove(i);
        }
        for (String themeName : themeNames) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(themeName);
            item.setSelected(themeName.equals(App.config.getTheme()));
            item.addActionListener(this::themeChanged);
            themeMenu.add(item);
        }
    }

    private void fontSizeChanged(ActionEvent actionEvent) {
        try {
            String selectedFontSize = actionEvent.getActionCommand();

            FlatAnimatedLafChange.showSnapshot();

            App.config.setFontSize(Integer.parseInt(selectedFontSize));
            App.config.save();

            Init.initGlobalFont();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getTabbedPane());

//                FlatLaf.updateUI();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "部分细节重启应用后生效！\n\n", "成功",
                    JOptionPane.INFORMATION_MESSAGE);

            initFontSizeMenu();

        } catch (Exception e1) {
            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "保存失败！\n\n" + e1.getMessage(), "失败",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
        }
    }


    private void fontFamilyChanged(ActionEvent actionEvent) {
        try {
            String selectedFamily = actionEvent.getActionCommand();

            FlatAnimatedLafChange.showSnapshot();

            App.config.setFont(selectedFamily);
            App.config.save();

            Init.initGlobalFont();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getTabbedPane());

//                FlatLaf.updateUI();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "部分细节重启应用后生效！\n\n", "成功",
                    JOptionPane.INFORMATION_MESSAGE);

            initFontFamilyMenu();

        } catch (Exception e1) {
            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "保存失败！\n\n" + e1.getMessage(), "失败",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
        }
    }

    private void themeChanged(ActionEvent actionEvent) {
        try {
            String selectedThemeName = actionEvent.getActionCommand();

            FlatAnimatedLafChange.showSnapshot();

            App.config.setTheme(selectedThemeName);
            App.config.save();

            Init.initTheme();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getTabbedPane());

//                FlatLaf.updateUI();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "部分细节重启应用后生效！\n\n", "成功",
                    JOptionPane.INFORMATION_MESSAGE);

            initThemesMenu();

        } catch (Exception e1) {
            JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "保存失败！\n\n" + e1.getMessage(), "失败",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
        }
    }

    private void keyMapActionPerformed() {
        try {
            KeyMapDialog dialog = new KeyMapDialog();

            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception e2) {
            logger.error(e2);
        }
    }

    private void aboutActionPerformed() {
        try {
            AboutDialog dialog = new AboutDialog();

            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception e2) {
            logger.error(e2);
        }
    }

    private void sysEnvActionPerformed() {
        try {
            SystemEnvResultDialog dialog = new SystemEnvResultDialog();

            dialog.appendTextArea("------------System.getenv---------------");
            Map<String, String> map = System.getenv();
            for (Map.Entry<String, String> envEntry : map.entrySet()) {
                dialog.appendTextArea(envEntry.getKey() + "=" + envEntry.getValue());
            }

            dialog.appendTextArea("------------System.getProperties---------------");
            Properties properties = System.getProperties();
            for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
                dialog.appendTextArea(objectObjectEntry.getKey() + "=" + objectObjectEntry.getValue());
            }

            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception e2) {
            logger.error("查看系统环境变量失败", e2);
        }
    }

    private void logActionPerformed() {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(SystemUtil.LOG_DIR));
        } catch (Exception e2) {
            logger.error("查看日志打开失败", e2);
        }
    }

    private void exitActionPerformed() {
        Init.shutdown();
    }

    private void settingActionPerformed() {
        try {
            SettingDialog dialog = new SettingDialog();

            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception e2) {
            logger.error(e2);
        }
    }
}
