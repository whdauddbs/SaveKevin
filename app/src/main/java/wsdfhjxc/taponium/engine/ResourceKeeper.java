package wsdfhjxc.taponium.engine;

import android.content.*;
import android.content.res.*;
import android.graphics.*;

import java.util.*;

public class ResourceKeeper {   // 사진과 폰트를 로딩하여 저장하는 클래스이다. 로딩한 클래스
    private final Resources resources;  // 리소스를 저장하는 객체
    private final Context context;  // 액티비티 저장하는 변수

    private final Map<String, Bitmap> bitmaps;  // 그림파일(비트맵)을 저장하는 맵
    private final Map<String, Typeface> typefaces;   // 폰트를 저장하는 맵

    public ResourceKeeper(Context context) {   // ResourceKeeper의 생성자. 액티비티의 리소스를 불러오고 객체 초기화
        resources = context.getResources();  //액티비티의 리소스(res폴더)에 접근
        this.context = context;   //액티비티

        bitmaps = new HashMap();   //맵개념 비트맵저장
        typefaces = new HashMap();   //맵개념 폰트저장
    }

    public boolean loadBitmap(String bitmapName) {   // 그림파일 이름을 입력받아 해당하는 파일의 비트맵과 이름을 맵에다가 저장
        int resourceId = resources.getIdentifier(bitmapName, "drawable", context.getPackageName());
        if (resourceId > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
            bitmaps.put(bitmapName, bitmap);
            return true;
        } else {
            return false;
        }
    }

    public void unloadBitmap(String bitmapName) {  // 불러온 비트맵 삭제
        Bitmap bitmap = bitmaps.get(bitmapName);
        if (bitmap != null) {
            bitmap.recycle();
            bitmaps.remove(bitmapName);
        }
    }

    public boolean loadTypeface(String fontName, String fileExtension) {  // 폰트 로딩
        String fontFileName = fontName + "." + fileExtension;
        Typeface typeface = Typeface.createFromAsset(resources.getAssets(), fontFileName);
        if (typeface != null) {
            typefaces.put(fontName, typeface);
            return true;
        } else {
            return false;
        }
    }

    // 불러온 폰트 삭제
    public void unloadTypeface(String fontName) {
        typefaces.remove(fontName);
    }

    // 이름을 입력받아 비트맵 반환. 불러온 사진만 반환 가능
    public Bitmap getBitmap(String bitmapName) {
        return bitmaps.get(bitmapName);
    }

    // 이름을 입력받아 폰트 반환. 불러온 폰트만 반환 가능
    public Typeface getTypeface(String fontName) {
        return typefaces.get(fontName);
    }

    // 불러온 폰트와 사진 삭제
    public void unloadEverything() {
        for (Bitmap bitmap : bitmaps.values()) {
            bitmap.recycle();
        }

        bitmaps.clear();
        typefaces.clear();
    }
}
