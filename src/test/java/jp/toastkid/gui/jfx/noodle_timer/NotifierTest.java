package jp.toastkid.gui.jfx.noodle_timer;

import org.junit.Test;

/**
 * {@link Notifier}'s test.
 * @author Toast kid
 *
 */
public class NotifierTest {

    /** test object. */
    private Notifier notifier;

    /**
     * ファイルがなくとも初期化できることを確認.
     */
    @Test
    public void testInit() {
        notifier = new Notifier.Builder().setPathToWav("notExist.wav").build();
        notifier.play();
    }

}
