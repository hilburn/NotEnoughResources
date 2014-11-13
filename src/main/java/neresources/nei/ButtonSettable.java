package neresources.nei;

import codechicken.nei.Button;

public abstract class ButtonSettable extends Button
{
    public ButtonSettable(int x, int y, String content)
    {
        this.x = x;
        this.y = y;
        this.label = content;
        this.w = 12;
        this.h = 12;
    }
}
