package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.print.Color;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class MazeOptionTest {
    @Test
    public void intOption() throws Exception {
        MazeOption mo = new MazeOption("myName", 27, 5, 67);
        assertEquals("myName", mo.getName());
        assertEquals(27, mo.getDefaultValue());
        assertEquals(5, (double)mo.getMin(), epsilon);
        assertEquals(67, (double)mo.getMax(), epsilon);
        assertEquals(1, (double)mo.getStep(), epsilon);
    }

    @Test
    public void setValue() throws Exception {
        MazeOption mo = new MazeOption("myName", 27, 5, 67);
        mo.setValue("gaga");
        assertEquals("gaga", mo.getDefaultValue());
    }

    @Test
    public void setEditorFluently() throws Exception {
        MazeOption mo = new MazeOption("myName", 27, 5, 67);
        assertEquals("gaga", mo.setEditor("gaga").getEditor());
    }

    @Test
    public void getLevel() throws Exception {
    }

    @Test
    public void setLevelFluently() throws Exception {
        MazeOption mo = new MazeOption("myName", 27, 5, 67);
        assertEquals(OptionLevel.Invisible, mo.setLevel(OptionLevel.Invisible).getLevel());
    }

    @Test
    public void convertAndValidateNullBoolean() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(false, mp.getBoolean("my"));
    }

    @Test
    public void convertAndValidateTrueBoolean() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", true);
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(true, mp.getBoolean("my"));
    }

    @Test
    public void convertAndValidateFalseBoolean() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", false);
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(false, mp.getBoolean("my"));
    }

    @Test
    public void convertAndValidateTrueBooleanFromString() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "true");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(true, mp.getBoolean("my"));
    }

    @Test
    public void convertAndValidateFalseBooleanFromString() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "false");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(false, mp.getBoolean("my"));
    }

    @Test
    public void convertAndValidateIncorrectBooleanStringUS() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "truje");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(!errors.isEmpty());
        assertEquals("Boolean format error. Is 'truje'. Must be either 'true' or 'false'.", errors.errorsForField("my").get(0));
    }

    @Test
    public void convertAndValidateIncorrectBooleanStringCs() throws Exception {
        MazeOption mo = new MazeOption("my", true);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "truje");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.forLanguageTag("cs-CZ"), "", errors);
        assert(!errors.isEmpty());
        assertEquals(         "Chyba formátu boolean. Je 'truje'. Musí být buď 'true' nebo 'false'.", errors.errorsForField("my").get(0));
    }

    @Test
    public void convertAndValidateInteger() throws Exception {
        MazeOption mo = new MazeOption("my", 1, -100, 100);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "50");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(50, mp.getInt("my"));
    }

    @Test
    public void convertAndValidateIncorrectIntegerStringUS() throws Exception {
        MazeOption mo = new MazeOption("my", 1, -100, 100);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "1xx");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(!errors.isEmpty());
        assertEquals("Integer format error.", errors.errorsForField("my").get(0));
    }

    @Test
    public void convertAndValidateIncorrectIntegerStringCS() throws Exception {
        MazeOption mo = new MazeOption("my", 1, -100, 100);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "1xx");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.forLanguageTag("cs-CZ"), "", errors);
        assert(!errors.isEmpty());
        assertEquals("Chyba formátu celého čísla.", errors.errorsForField("my").get(0));
    }

    @Test
    public void convertAndValidateDouble() throws Exception {
        MazeOption mo = new MazeOption("my", 0.5, -100, 100, 1);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "50.5");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(errors.isEmpty());
        assertEquals(50.5, mp.getDouble("my"), epsilon);
    }

    @Test
    public void convertAndValidateIncorrectDoubleStringUS() throws Exception {
        MazeOption mo = new MazeOption("my", 0.5, -100, 100, 1);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "1xx");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(!errors.isEmpty());
        assertEquals("Double format error.", errors.errorsForField("my").get(0));
    }

    @Test
    public void convertAndValidateIncorrectRangeUS() throws Exception {
        MazeOption mo = new MazeOption("my", 1, -120, 100);
        MazeProperties mp = new MazeProperties();
        mp.put("my", "1000");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.US, "", errors);
        assert(!errors.isEmpty());
        assertEquals("Range error. Value should be between -120 and 100 but is 1,000.", errors.errorsForField("my").get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void convertAndValidateColor() throws Exception {
        MazeOption mo = new MazeOption("my",  new Color(0,0,0,0));
        MazeProperties mp = new MazeProperties();
        mp.put("my", "1xx");
        MazeValidationErrors errors = new MazeValidationErrors();
        mo.convertAndValidate(mp, Locale.forLanguageTag("cs-CZ"), "", errors);
    }

    private static final double epsilon = 1e-6;
}