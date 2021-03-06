package wsdfhjxc.taponium.game;

import android.graphics.*;

import wsdfhjxc.taponium.engine.*;

public class BoardRenderer { // 비트맵 및 스마트폰 크기에 따라 보드판 이미지를 가져오는 클래스
    private final Board board;

    private Bitmap boardPanelBitmap;
    private Rect boardPanelRect;
    private Flex boardPanelFlex;

    private Flex boardAreaFlex;
    private Flex boardSlotFlex;
    private Flex boardSlotSpacerFlex;

    // screw sprite sheets, spare images are enough for this
    //햄스터 및 토끼의 사진값 설정
    private Bitmap hamsterBitmap[] = new Bitmap[2], deadHamsterBitmap[][] = new Bitmap[2][4];
    private Bitmap bunnyBitmap, deadBunnyBitmap;

    private Flex hamsterFlex;
    private Flex bunnyFlex;

    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public BoardRenderer(Board board, ResourceKeeper resourceKeeper, FlexConfig flexConfig,
                         Flex boardAreaFlex, Flex boardSlotFlex, Flex boardSlotSpacerFlex) {
        this.board = board;
        //보드 크기 저장
        this.boardAreaFlex = boardAreaFlex;
        this.boardSlotFlex = boardSlotFlex;
        this.boardSlotSpacerFlex = boardSlotSpacerFlex;
        boardPanelBitmap = resourceKeeper.getBitmap("board_panel");
        boardPanelRect = new Rect(0, 0, boardPanelBitmap.getWidth(), boardPanelBitmap.getHeight());
        boardPanelFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(boardPanelBitmap.getWidth(), boardPanelBitmap.getHeight()), true,
                new Point(-boardPanelBitmap.getWidth() / 2, -boardPanelBitmap.getHeight()),
                flexConfig);

        for(int i = 0;i<2;i++){
            hamsterBitmap[i] = resourceKeeper.getBitmap("hamster" + i);
            for(int j=0;j<4;j++){
                deadHamsterBitmap[i][j] = resourceKeeper.getBitmap("dead_hamster"+i+"_"+j);
            }
        }
        bunnyBitmap = resourceKeeper.getBitmap("bunny");
        deadBunnyBitmap = resourceKeeper.getBitmap("dead_bunny");

        //햄스터의 크기 저장
        hamsterFlex = new Flex(new PointF(0f, 0f), true,
                new PointF(182f, 207f), true,
                new Point(), flexConfig);
        //토끼의 크기 저장
        bunnyFlex = new Flex(new PointF(0f, 0f), true,
                new PointF(182f, 302f), true,
                new Point(), flexConfig);
    }

    public void render(Canvas canvas, Paint paint, double alpha) {
        canvas.drawBitmap(boardPanelBitmap, boardPanelRect, boardPanelFlex.getRect(), paint);

        //x,y 좌표값에 따라 보드값 설정
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

                Bitmap bitmap;
                Flex flex;
                //각 상황에 맞는 이미지를 가져옴
                switch (slot.getContentType()) {
                    case HAMSTER: { //살아있는 햄스터의 경우
                        bitmap = hamsterBitmap[slot.getRandomContentValue()];
                        flex = hamsterFlex;
                    } break;
                    case DEAD_HAMSTER: { //죽은 햄스터의 경우
                        bitmap = deadHamsterBitmap[slot.getRandomContentValue()][slot.getStdXY()];
                        flex = hamsterFlex;
                    } break;
                    case BUNNY: { //살아있는 토끼의 경우
                        bitmap = bunnyBitmap;
                        flex = bunnyFlex;
                    } break;
                    case DEAD_BUNNY: { //죽은 토끼의 경우
                        bitmap = deadBunnyBitmap;
                        flex = bunnyFlex;
                    } break;
                    default: {
                        continue;
                    }
                }

                double angled = Math.toRadians(slot.getInterpolatedDurationRatio(alpha) * 180.0);
                int offsetR = (int) (Math.sin(angled) * bitmap.getHeight());
                int offset = (int) (Math.sin(angled) * flex.getSize().y);

                srcRect.set(0, 0, bitmap.getWidth(), offsetR);
                dstRect.set(x, y - flex.getSize().y + (flex.getSize().y - offset),
                        x + flex.getSize().x, y);

                canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
            }
        }
    }
}
