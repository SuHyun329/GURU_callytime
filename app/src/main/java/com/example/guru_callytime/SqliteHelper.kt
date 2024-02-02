package com.example.guru_callytime

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guru_callytime.ui.memo.Memo

// SQLiteOpenHelper 상속받아 SQLite 를 사용
class SqliteHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    //onCreate(), onUpgrade() 두가지 메소드를 오버라이드
    override fun onCreate(db: SQLiteDatabase?) {
        //테이블을 생성할 쿼리
        val create = "create table memo (id integer primary key, content text, datetime integer)"
        //실행
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //insert
    fun insertMemo(memo:Memo){
        val values = ContentValues()
        //넘겨줄 컬럼의 매개변수
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)
        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.insert("memo",null,values)
        wd.close()
    }


    //select
    fun selectMemo():MutableList<Memo>{
        val list = mutableListOf<Memo>()
        //전체조회
        val selectAll = "select * from memo"
        //읽기전용 변수
        val rd = readableDatabase
        //데이터 받아오기
        val cursor = rd.rawQuery(selectAll,null)

        //반복문을 사용하여 list 에 데이터를 넘겨주기
        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            list.add(Memo(id,content,datetime))
        }
        cursor.close()
        rd.close()

        return list
    }

    //update
    fun updateMemo(memo:Memo){
        val values = ContentValues()

        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        val wd = writableDatabase
        wd.update("memo",values,"id=${memo.id}",null)
        wd.close()

    }

    //delete
    fun deleteMemo(memo:Memo){
        val delete = "delete from Memo where id = ${memo.id}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }

}