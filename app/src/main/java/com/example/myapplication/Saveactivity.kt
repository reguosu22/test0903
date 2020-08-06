package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBObject

class Saveactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saveactivity)
        NCMB.initialize(
            this.applicationContext,
            "9d7360c45809fb8de237cd6a66fd7d041e1a7093a3db547115d3193256311325",
            "c39345676c30089f40499227d61a45e73461374dd3d1837c71ab93c61ec25980"
        )



        // クラスのNCMBObjectを作成
        val obj = NCMBObject("TestClass")

// オブジェクトの値を設定
        try {
            obj.put("message", "Hello, NCMB!")
        } catch (e: NCMBException) {
            e.printStackTrace()
        }

// データストアへの登録
        obj.saveInBackground { e ->
            if (e != null) {
                //保存に失敗した場合の処理
                println("failed")
            } else {
                println("step6")
                //保存に成功した場合の処理
                //println("succesfull")
                //obj.put("message", "Hello, NCMB!")
            }
        }


    }


}