package com.wsdfhjxc.taponium.game;

import android.graphics.*;

import com.wsdfhjxc.taponium.engine.*;

public class BoardRenderer {
    private final Board board;

    private Bitmap boardPanelImage;
    private Rect boardPanelRect;
    private Flex boardPanelFlex;

    private Flex boardAreaFlex;
    private Flex boardSlotFlex;
    private Flex boardSlotSpacerFlex;

    // screw sprite sheets, spare images are enough for this
    private Bitmap hamsterImage, deadHamsterImage;
    private Bitmap bunnyImage, deadBunnyImage;

    private Flex hamsterFlex;
    private Flex bunnyFlex;

    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public BoardRenderer(Board board, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        this.board = board;

        boardAreaFlex = new Flex(new PointF(0.5f, 1f), false,
                                 new PointF(814f, 714f), true,
                                 new Point(-814 / 2, -993), flexConfig);

        boardSlotFlex = new Flex(new PointF(0f, 0f), true,
                                 new PointF(183f, 156f), true,
                                 new Point(), flexConfig);

        boardSlotSpacerFlex = new Flex(new PointF(0f, 0f), true,
                                       new PointF(138f, 128f), true,
                                       new Point(), flexConfig);

        boardPanelImage = resourceKeeper.getBitmap("board_panel");
        boardPanelRect = new Rect(0, 0, boardPanelImage.getWidth(), boardPanelImage.getHeight());
        boardPanelFlex = new Flex(new PointF(0.5f, 1f), false,
                                  new PointF(boardPanelImage.getWidth(), boardPanelImage.getHeight()), true,
                                  new Point(-boardPanelImage.getWidth() / 2, -boardPanelImage.getHeight()),
                                  flexConfig);

        hamsterImage = resourceKeeper.getBitmap("hamster");
        bunnyImage = resourceKeeper.getBitmap("bunny");
        deadHamsterImage = resourceKeeper.getBitmap("dead_hamster");
        deadBunnyImage = resourceKeeper.getBitmap("dead_bunny");

        hamsterFlex = new Flex(new PointF(0f, 0f), true,
                            new PointF(182f, 207f), true,
                            new Point(), flexConfig);

        bunnyFlex = new Flex(new PointF(0f, 0f), true,
                             new PointF(182f, 302f), true,
                             new Point(), flexConfig);
    }

    public void render(Canvas canvas, Paint paint, double alpha) {
        canvas.drawBitmap(boardPanelImage, boardPanelRect, boardPanelFlex.getRect(), paint);

        int beginX = boardAreaFlex.getPosition().x;
        int beginY = boardAreaFlex.getPosition().y;
        int stepX = boardSlotFlex.getSize().x + boardSlotSpacerFlex.getSize().x;
        int stepY = boardSlotFlex.getSize().y + boardSlotSpacerFlex.getSize().y;

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                int x = beginX + stepX * j;
                int y = beginY + stepY * i + boardSlotFlex.getSize().y;

                Slot slot = board.getSlot(j, i);

                if (slot.isFree()) {
                    continue;
                }

                Bitmap image;
                Flex flex;

                switch (slot.getContentType()) {
                    case HAMSTER: {
                        image = hamsterImage;
                        flex = hamsterFlex;
                    } break;
                    case DEAD_HAMSTER: {
                        image = deadHamsterImage;
                        flex = hamsterFlex;
                    } break;
                    case BUNNY: {
                        image = bunnyImage;
                        flex = bunnyFlex;
                    } break;
                    case DEAD_BUNNY: {
                        image = deadBunnyImage;
                        flex = bunnyFlex;
                    } break;
                    default: {
                        continue;
                    }
                }

                double angled = Math.toRadians(slot.getInterpolatedDurationRatio(alpha) * 180.0);
                int offsetR = (int) (Math.sin(angled) * image.getHeight());
                int offset = (int) (Math.sin(angled) * flex.getSize().y);

                srcRect.set(0, 0, image.getWidth(), offsetR);
                dstRect.set(x, y - flex.getSize().y + (flex.getSize().y - offset),
                            x + flex.getSize().x, y);

                canvas.drawBitmap(image, srcRect, dstRect, paint);
            }
        }
    }
}