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

public class MinionAttackTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private Player whoAmIPlaying;
    private CardFigure draggedActor;
    private int lastX;
    private int lastY;
    private int orgX;
    private int orgY;

    public MinionAttackTool(DrawingEditor editor, Game game, Player whoAmIPlaying) {
        this.editor = editor;
        this.game = game;
        this.whoAmIPlaying = whoAmIPlaying;
    }


    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        // Note: The HotSeatStateTool should ensure that this tool
        // is only active iff there is a card figure below (x,y)
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
        draggedActor = (CardFigure) figureAtPosition;
        // Move the card to the visual top
        model.zOrder(draggedActor, ZOrder.TO_TOP);
        // And remember where the card was dragged from (orgX, orgY)
        lastX = x; lastY = y;
        orgX = x; orgY = y;
    }


    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        // compute relative movement
        draggedActor.moveBy(x - lastX, y - lastY);
        // update last position
        lastX = x; lastY = y;
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        editor.drawing().zOrder(draggedActor, ZOrder.TO_BOTTOM);
        Figure targetFigure = editor.drawing().findFigure(x, y);
        Card attackingCard = draggedActor.getAssociatedCard();

        if(draggedActor != null && targetFigure instanceof HotStoneFigure){
            HotStoneFigure hsf = (HotStoneFigure) targetFigure;

            if(hsf.getType() == HotStoneFigureType.MINION_FIGURE){
                Card defendingCard = ((CardFigure) targetFigure).getAssociatedCard();

                Status status = game.attackCard(whoAmIPlaying, attackingCard, defendingCard);
                editor.showStatus("" + whoAmIPlaying +
                        " tried to attack " + defendingCard.getName() +
                        " with his " + attackingCard.getName() +
                        ". Status = " + status);
            } else if(hsf.getType() == HotStoneFigureType.HERO_FIGURE){
                Status status = game.attackHero(whoAmIPlaying, attackingCard);
                editor.showStatus("" + whoAmIPlaying +
                        " tried to attack " + Player.computeOpponent(whoAmIPlaying) + "'s hero" +
                        " with " + attackingCard.getName() +
                        ". Status = " + status);
            }
        }
        if(draggedActor != null){
            draggedActor.moveBy( orgX - x, orgY - y );
        }
        draggedActor = null;
    }


}