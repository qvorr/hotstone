package hotstone.view.tool;

import hotstone.framework.*;
import hotstone.view.GfxConstants;
import hotstone.view.figure.CardFigure;
import hotstone.view.figure.HeroFigure;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.*;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class UsePowerTool extends NullTool{
    private DrawingEditor editor;
    private Game game;
    private Player whoAmIPlaying;

    public UsePowerTool(DrawingEditor editor, Game game, Player whoAmIPlaying){
        this.editor = editor;
        this.game = game;
        this.whoAmIPlaying = whoAmIPlaying;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y){
        Drawing model = editor.drawing();
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());

        Status status = game.usePower(whoAmIPlaying);

        editor.showStatus("" + whoAmIPlaying + " tried using their hero power. Status = " + status );
    }

}
