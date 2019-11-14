package pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools;

import com.vaadin.ui.Button;

public class MagicClick {

    private short count = 0;

    public void perform(Button button){
        if(getCount()==0){
            setCount((short) 1);
            button.click();
            setCount((short) 0);
        }
    }

    public MagicClick(short count){
        this.count = count;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }

}
