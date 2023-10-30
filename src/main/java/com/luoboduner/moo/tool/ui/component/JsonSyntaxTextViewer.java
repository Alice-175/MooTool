package com.luoboduner.moo.tool.ui.component;

import com.formdev.flatlaf.FlatLaf;
import com.luoboduner.moo.tool.App;
import com.luoboduner.moo.tool.ui.form.func.TimeConvertForm;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JsonSyntaxTextViewer extends RSyntaxTextArea {
    public JsonSyntaxTextViewer() {

        addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(e.getURL().toString()));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setDoubleBuffered(true);
        
        updateTheme();
    }

    public void updateTheme() {
        try {
            Theme theme;
            if (FlatLaf.isLafDark()) {
                theme = Theme.load(JsonSyntaxTextViewer.class.getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            } else {
                theme = Theme.load(JsonSyntaxTextViewer.class.getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
            }
            theme.apply(this);
        } catch (IOException ioe) { // Never happens
            ioe.printStackTrace();
        }

        //        setCurrentLineHighlightColor(new Color(52, 52, 52));
//        setUseSelectedTextColor(true);
//        setSelectedTextColor(new Color(50, 50, 50));

        // 初始化背景色
//        Style.blackTextArea(this);
        setBackground(TimeConvertForm.getInstance().getTimeHisTextArea().getBackground());
        // 初始化边距
        setMargin(new Insets(10, 10, 10, 10));

        // 初始化字体
        String fontName = App.config.getJsonBeautyFontName();
        int fontSize = App.config.getJsonBeautyFontSize();
        if (fontSize == 0) {
            fontSize = getFont().getSize() + 2;
        }
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        setFont(font);

        setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        setCodeFoldingEnabled(true);

        setBackground(UIManager.getColor("Editor.background"));
        setCaretColor(UIManager.getColor("Editor.caretColor"));
        setSelectionColor(UIManager.getColor("Editor.selectionBackground"));
        setCurrentLineHighlightColor(UIManager.getColor("Editor.currentLineHighlight"));
        setMarkAllHighlightColor(UIManager.getColor("Editor.markAllHighlightColor"));
        setMarkOccurrencesColor(UIManager.getColor("Editor.markOccurrencesColor"));
        setMatchedBracketBGColor(UIManager.getColor("Editor.matchedBracketBackground"));
        setMatchedBracketBorderColor(UIManager.getColor("Editor.matchedBracketBorderColor"));
        setPaintMatchedBracketPair(true);
        setAnimateBracketMatching(false);
    }
}
