package com.cis.kotlinsqlite2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

// SQLite 표준 쿼리 사용방법이 아니라 제공된 클래스를 이용해서 처리하는 방법을 연습해
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertBtn.setOnClickListener { view ->
            val helper = DBHelper(this)
            val db = helper.writableDatabase

            /*
            일반적인 sqlite 사용법
            val sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.format(Date())

            val arg1 = arrayOf("문자열1", "100", "11.11", date)
            val arg2 = arrayOf("문자열2", "200", "22.22", date)

            db.execSQL(sql, arg1)
            db.execSQL(sql, arg2)
            */

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.format(Date())

            // ContentValues() : 어떤 컬럼에 어떤 값을 넣겠다를 지정하는 메소드. 1:1로 매칭됨
            val cv1 = ContentValues()
            cv1.put("textData", "문자열1")
            cv1.put("intData", 100)
            cv1.put("floatData", 11.11)
            cv1.put("dateData", date)

            db.insert("TestTable", null, cv1)

            val cv2 = ContentValues()
            cv2.put("textData", "문자열2")
            cv2.put("intData", 200)
            cv2.put("floatData", 22.22)
            cv2.put("dateData", date)

            db.insert("TestTable", null, cv2)

            db.close()

            tv.text = "저장 완료"
        }

        selectBtn.setOnClickListener { view ->
            selectData(this)
        }

        updateBtn.setOnClickListener { view ->
            val helper = DBHelper(this)
            val db = helper.writableDatabase

//            val sql = "update TestTable set textData=? where idx=?"
//            val args = arrayOf("문자열3", "1")

//            db.execSQL(sql, args)

            val cv = ContentValues()
            cv.put("textData", "문자열3")
            val where = "idx=?"
            val args = arrayOf("1")

            db.update("TestTable", cv, where, args)

            db.close()

            tv.text = "수정 완료"
        }

        deleteBtn.setOnClickListener { view ->
            val helper = DBHelper(this)
            val db = helper.writableDatabase

//            val sql = "delete from TestTable where idx=?"
//            val args = arrayOf("1")

//            db.execSQL(sql, args)

            val where = "idx=?"
            val args = arrayOf("1")

            db.delete("TestTable", where, args)

            db.close()

            tv.text = "삭제 완료"
        }
    }

    fun selectData(context: Context) {
        val helper = DBHelper(context)
        val db = helper.writableDatabase

//        val sql = "select * from TestTable"

//        val cursor: Cursor = db.rawQuery(sql, null)

        // 첫 번째 : 테이블의 이름
        // 두 번째 : 가져올 컬럼 이름의 문자열 배열. null을 넣어주면 모든 컬럼을 가져온다.
        // 세 번째 : 조건절. 값이 들어가는 부분은 ?로 작성한다.
        // 네 번째 : 조건절의 ?에 셋팅될 값의 문자열 배열
        // 다섯 번째 : group by
        // 여섯 번째 : having 절
        // 여섯 번째 : 정렬 기준. order by
        val cursor = db.query(
            "TestTable",
            null,
            null,
            null,
            null,
            null,
            null)

        tv.text = ""

        while (cursor.moveToNext()) {
            val idxPos = cursor.getColumnIndex("idx")
            val textDataPos = cursor.getColumnIndex("textData")
            val intDataPos = cursor.getColumnIndex("intData")
            val floatDataPos = cursor.getColumnIndex("floatData")
            val dateDataPos = cursor.getColumnIndex("dateData")

            val idx = cursor.getInt(idxPos)
            val textData = cursor.getString(textDataPos)
            val intData = cursor.getInt(intDataPos)
            val floatData = cursor.getDouble(floatDataPos)
            val dateData = cursor.getString(dateDataPos)

            tv.append("idx : ${idx}\n")
            tv.append("textData : ${textData}\n")
            tv.append("intData : ${intData}\n")
            tv.append("floatData : ${floatData}\n")
            tv.append("dateData : ${dateData}\n\n")
        }

        db.close()
    }
}
