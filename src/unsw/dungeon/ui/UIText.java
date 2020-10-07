package unsw.dungeon.ui;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Create text for the UI
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class UIText extends Text {

	/**
	 * Class constructor for UIText
	 * @param isWrap boolean value for wrapping the text
	 */
    public UIText(boolean isWrap) {
        this.setFill(Paint.valueOf("#ffffff"));
        Font bitFont = Font.loadFont(getClass().getResourceAsStream("/8bit.ttf"), 12);
        this.setFont(bitFont);

        if (isWrap) {
            this.setTranslateX(4);
        }
    }
    
    /**
     * Class constructor for UIText
     * @param isWrap boolean value for wrapping the text
     * @param fontSize int value for font size
     */
    public UIText(boolean isWrap, int fontSize) {
        this.setFill(Paint.valueOf("#ffffff"));
        Font bitFont = Font.loadFont(getClass().getResourceAsStream("/8bit.ttf"), fontSize);
        this.setFont(bitFont);

        if (isWrap) {
            this.setTranslateX(4);
        }
    }
}
