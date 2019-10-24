package com.luoboduner.moo.tool.ui.listener.func;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.luoboduner.moo.tool.App;
import com.luoboduner.moo.tool.dao.THostMapper;
import com.luoboduner.moo.tool.domain.THost;
import com.luoboduner.moo.tool.ui.form.func.HostForm;
import com.luoboduner.moo.tool.util.MybatisUtil;
import com.luoboduner.moo.tool.util.SqliteUtil;
import com.luoboduner.moo.tool.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

/**
 * <pre>
 * Host事件监听
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/9/7.
 */
@Slf4j
public class HostListener {

    private static THostMapper hostMapper = MybatisUtil.getSqlSession().getMapper(THostMapper.class);

    public static String selectedNameHost;

    public static void addListeners() {
        HostForm hostForm = HostForm.getInstance();

        hostForm.getSaveButton().addActionListener(e -> save(true));

        hostForm.getSwitchButton().addActionListener(e -> ThreadUtil.execute(() -> {
            String hostText = hostForm.getTextArea().getText();
            HostForm.setHost(selectedNameHost, hostText);
            save(false);
        }));

        // 点击左侧表格事件
        hostForm.getNoteListTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ThreadUtil.execute(() -> {
                    refreshHostContentInTextArea();
                });
                super.mousePressed(e);
            }
        });

        // 文本域按键事件
        hostForm.getTextArea().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_S) {
                    String now = SqliteUtil.nowDateForSqlite();
                    if (selectedNameHost != null) {
                        THost tHost = new THost();
                        tHost.setName(selectedNameHost);
                        tHost.setContent(hostForm.getTextArea().getText());
                        tHost.setModifiedTime(now);

                        hostMapper.updateByName(tHost);
                    } else {
                        String tempName = "未命名_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd_HH-mm-ss");
                        String name = JOptionPane.showInputDialog("名称", tempName);
                        if (StringUtils.isNotBlank(name)) {
                            THost tHost = new THost();
                            tHost.setName(name);
                            tHost.setContent(hostForm.getTextArea().getText());
                            tHost.setCreateTime(now);
                            tHost.setModifiedTime(now);

                            hostMapper.insert(tHost);
                            HostForm.initListTable();
                            selectedNameHost = name;
                        }
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
            }
        });

        // 删除按钮事件
        hostForm.getDeleteButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                int[] selectedRows = hostForm.getNoteListTable().getSelectedRows();

                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(App.mainFrame, "请至少选择一个！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int isDelete = JOptionPane.showConfirmDialog(App.mainFrame, "确认删除？", "确认", JOptionPane.YES_NO_OPTION);
                    if (isDelete == JOptionPane.YES_OPTION) {
                        DefaultTableModel tableModel = (DefaultTableModel) hostForm.getNoteListTable().getModel();

                        for (int i = selectedRows.length; i > 0; i--) {
                            int selectedRow = hostForm.getNoteListTable().getSelectedRow();
                            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                            hostMapper.deleteByPrimaryKey(id);

                            tableModel.removeRow(selectedRow);
                        }
                        selectedNameHost = null;
                        HostForm.initListTable();
                    }
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.mainFrame, "删除失败！\n\n" + e1.getMessage(), "失败",
                        JOptionPane.ERROR_MESSAGE);
                log.error(e1.toString());
            }
        }));

        // 添加按钮事件
        hostForm.getAddButton().addActionListener(e -> {
            hostForm.getTextArea().setText("");
            hostForm.getTextArea().setEditable(true);
            selectedNameHost = null;
        });

        // 左侧列表鼠标点击事件（显示下方删除按钮）
        hostForm.getNoteListTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = hostForm.getNoteListTable().getSelectedRow();
                String name = hostForm.getNoteListTable().getValueAt(selectedRow, 1).toString();
                if (HostForm.SYS_CURRENT_HOST_NAME.equals(name)) {
                    hostForm.getDeletePanel().setVisible(false);
                } else {
                    hostForm.getDeletePanel().setVisible(true);
                }
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

        // 文本域鼠标点击事件，隐藏删除按钮
        hostForm.getTextArea().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hostForm.getDeletePanel().setVisible(false);
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

        // 左侧列表按键事件（重命名）
        hostForm.getNoteListTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent evt) {

            }

            @Override
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedRow = hostForm.getNoteListTable().getSelectedRow();
                    int noteId = Integer.parseInt(String.valueOf(hostForm.getNoteListTable().getValueAt(selectedRow, 0)));
                    String name = String.valueOf(hostForm.getNoteListTable().getValueAt(selectedRow, 1));
                    if (StringUtils.isNotBlank(name)) {
                        THost tHost = new THost();
                        tHost.setId(noteId);
                        tHost.setName(name);
                        try {
                            hostMapper.updateByPrimaryKeySelective(tHost);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(App.mainFrame, "重命名失败，可能和已有笔记重名");
                            HostForm.initListTable();
                            log.error(e.toString());
                        }
                    }
                }
            }
        });

    }

    public static void refreshHostContentInTextArea() {
        HostForm hostForm = HostForm.getInstance();
        int selectedRow = hostForm.getNoteListTable().getSelectedRow();
        String name = hostForm.getNoteListTable().getValueAt(selectedRow, 1).toString();
        String content;
        if (HostForm.SYS_CURRENT_HOST_NAME.equals(name)) {
            if (SystemUtil.isWindowsOs()) {
                content = FileUtil.readUtf8String(HostForm.WIN_HOST_FILE_PATH);
            } else {
                content = HostForm.NOT_SUPPORTED_TIPS;
            }
            hostForm.getTextArea().setEditable(false);
            hostForm.getSwitchButton().setVisible(false);
        } else {
            selectedNameHost = name;
            THost tHost = hostMapper.selectByName(name);
            content = tHost.getContent();
            hostForm.getTextArea().setEditable(true);
            hostForm.getSwitchButton().setVisible(true);
        }
        hostForm.getTextArea().setText(content);
    }

    private static void save(boolean needRename) {
        if (StringUtils.isEmpty(selectedNameHost)) {
            selectedNameHost = "未命名_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd_HH-mm-ss");
        }
        String name = selectedNameHost;
        if (needRename) {
            name = JOptionPane.showInputDialog("名称", selectedNameHost);
        }
        if (StringUtils.isNotBlank(name)) {
            THost tHost = hostMapper.selectByName(name);
            if (tHost == null) {
                tHost = new THost();
            }
            String now = SqliteUtil.nowDateForSqlite();
            tHost.setName(name);
            tHost.setContent(HostForm.getInstance().getTextArea().getText());
            tHost.setCreateTime(now);
            tHost.setModifiedTime(now);
            if (tHost.getId() == null) {
                hostMapper.insert(tHost);
                HostForm.initListTable();
                selectedNameHost = name;
            } else {
                hostMapper.updateByPrimaryKey(tHost);
            }
        }
    }
}
