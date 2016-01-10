package com.shanghai;
/**
 * .   如果你认为你败了，那你就一败涂地；
 * .   如果你认为你不敢，那你就会退缩；
 * .   如果你想赢但是认为你不能；
 * .   那么毫无疑问你就会失利；
 * .   如果你认为你输了，你就输了；
 * .   我们发现成功是从一个人的意志开始的；
 * .   成功是一种心态。
 * .   生活之战中，
 * .   胜利并不属于那些更强和更快的人，
 * .   胜利者终究是认为自己能行的人。
 * .
 * .   If you think you are beaten,you are;
 * .   If you think you dare not,you don't;
 * .   If you can to win but think you can't;
 * .   It's almost a cinch you won't.
 * .   If you think you'll lose,you're lost;
 * .   For out of the world we find Success begins with a fellow's will;
 * .   It's all in a state of mind.
 * .   Life's battles don't always go to the stronger and faster man,But sooner or later the man who wins Is the man who thinks he can.
 * .
 * .   You can you do.  No can no bb.
 * .
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/18  12:51
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient
 * The system has the function of camera and photo album test
 */
public class CameraTest extends Activity {
    private final int CAMERA = 0x01;
    private final int PICTURE = 0x02;
    private ImageView im_cameraTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameratest);
        im_cameraTest = (ImageView) findViewById(R.id.im_cameraTest);
        findViewById(R.id.openCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CAMERA);
            }
        });
        findViewById(R.id.openPhotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMERA ||  requestCode == PICTURE)&& resultCode == Activity.RESULT_OK && null != data) {
            if (requestCode == CAMERA){
                Bundle bundle = data.getExtras();
                //获取相机返回的数据，并转换为图片格式
                Bitmap bitmap = (Bitmap) bundle.get("data");
                im_cameraTest.setImageBitmap(bitmap);
            }
            if ( requestCode == PICTURE){
                Uri selectedImage = data.getData();
                String[] filePathColumns={MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath= c.getString(columnIndex);
                c.close();
                im_cameraTest.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }

        }
    }
}
