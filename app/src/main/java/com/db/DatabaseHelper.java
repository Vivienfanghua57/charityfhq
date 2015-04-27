package com.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.AlphabetIndexer;

import com.chat.MessageEntity;
import com.entity.ChatListEntity;
import com.entity.FocusEntity;
import com.entity.ForumThreadExtendEntity;
import com.entity.MemberEntity;
import com.entity.UserEntitiy;
import com.util.GetSpell;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String name = "database.db";// 数据库名称
    private static final int version = 1;// 数据库版本
//	/** 关注人名字 **/
//	public static final String FOCUS_NAME = "focusname";
////	/** 关注表 **/
////	public static final String TABLE_FOCUS_NAME = "pre_common_focus";
//	/** 关注人的名字对应的首字母 **/
//	public static final String TABLE_FOCUS_NAME_INITIAL = "initial";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log.e("DBOpenHelper",
        // "DBOpenHelperDBOpenHelperDBOpenHelperDBOpenHelper");
        db.execSQL("CREATE TABLE IF NOT EXISTS users("
                + "userID INTEGER NOT NULL PRIMARY KEY, "
                + "userName char(30) NOT NULL,"
                + "password char(32) NOT NULL,"
                + "userAvatar char(30) NULL);");
        //聊天列表，村粗聊过天的对象id,到底是用properties，preference存储还是数据库
        db.execSQL("CREATE TABLE IF NOT EXISTS chatlist("
                + "chatFrom char(30) NOT NULL,"
                + "chatto_id char(30) NOT NULL,"
                + "chatto_avatar char(18) NULL,"
                + "PRIMARY KEY (chatFrom,chatto_id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS chatrec("
                + "chatID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + "chatFrom char(30) NOT NULL,"
                + "chatto_id char(30) NOT NULL,"
                + "chatTo char(30) NOT NULL,"// userName,chatFrom,chatTo,chatDate,chatContent
                + "chatDate char(30) NOT NULL,"
                + "type tinyint NOT NULL,"
                + "chatContent char(256) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS goods("
                + "gID                  bigint NOT NULL PRIMARY KEY,"
                + "name                 CHAR(30) NOT NULL,"
                + "FID                  CHAR(200) NULL);");
        Log.e("DBOpenHelper", "chengogng");
        db.execSQL("CREATE TABLE IF NOT EXISTS goodsPara("
                + "gID                  bigint NOT NULL,"
                + "prID                 SMALLINT NOT NULL,"
                + "value                CHAR(18) NOT NULL,"
                + "PRIMARY KEY (prID,gID));");
        db.execSQL("CREATE TABLE IF NOT EXISTS goodsSite("
                + "gsID                 INTEGER NOT NULL PRIMARY KEY,"
                + "x                    DEC(8,4) NOT NULL,"
                + "y                    DEC(8,4) NOT NULL,"
                + "gID                  bigint NULL,"
                + "changeTime           DATE NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS mailAddress("
                + "addrID               CHAR(18) NOT NULL PRIMARY KEY,"
                + "ifDefault            TINYINT NOT NULL,"
                + "address              CHAR(50) NOT NULL,"
                + "zipcode              CHAR(6) NOT NULL,"
                + "name                 CHAR(20) NOT NULL,"
                + "ifpublic             TINYINT NOT NULL,"
                + "ifSync               TINYINT NOT NULL,"
                + "phone                CHAR(20) NOT NULL,"
                + "country              CHAR(20) NOT NULL,"
                + "province             CHAR(20) NOT NULL,"
                + "city                 CHAR(20) NOT NULL,"
                + "district             CHAR(20) NOT NULL,"
                + "uid                  mediumint(8) NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS markThread("
                + "uID                  BIGINT NOT NULL,"
                + "dt                   DATETIME NOT NULL,"
                + "ifSync               TINYINT NOT NULL,"
                + "ifDel                TINYINT NOT NULL,"
                + "tid                  mediumint(8) NOT NULL,"
                + "PRIMARY KEY (uID,tid));");
        db.execSQL("CREATE TABLE IF NOT EXISTS paras("
                + "prID                 SMALLINT NOT NULL PRIMARY KEY,"
                + "descr                CHAR(100) NOT NULL,"
                + "status               TINYINT NOT NULL,"
                + "fid                  mediumint(8) NULL,"
                + "ifNec                CHAR(18) NULL,"
                + "options              varCHAR(200) NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS postAttachments("
                + "aID                  CHAR(18) NOT NULL PRIMARY KEY,"
                + "fType                CHAR(18) NULL,"
                + "path                 CHAR(18) NULL,"
                + "fName                CHAR(18) NULL,"
                + "pid                  int(10) NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS common_focus("
                + "uid                  mediumint(8) NOT NULL,"
                + "focusname             char(15) NOT NULL DEFAULT '',"
                + "focusID              CHAR(18) NULL,"
                + "focusTime            CHAR(18) NULL,"
                + "ID                   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "initial              char(4) NOT NULL DEFAULT 'A',"// 首字母
                + "fType                TINYINT NULL);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS pre_common_member("
//				+ "uid                  INTEGER  PRIMARY KEY AUTOINCREMENT,"
//				+ "email                char(40) NOT NULL DEFAULT '',"
//				+ "username             char(15) NOT NULL DEFAULT '',"
//				+ "password             char(32) NOT NULL DEFAULT '',"
//				+ "status               tinyint(1) NOT NULL DEFAULT '0',"
//				+ "emailstatus          tinyint(1) NOT NULL DEFAULT '0',"
//				+ "avatarstatus         tinyint(1) NOT NULL DEFAULT '0',"
//				+ "videophotostatus     tinyint(1) NOT NULL DEFAULT '0',"
//				+ "adminid              tinyint(1) NOT NULL DEFAULT '0',"
//				+ "groupexpiry          int(10) NOT NULL DEFAULT '0',"
//				+ "extgroupids          char(20) NOT NULL DEFAULT '',"
//				+ "regdate              int(10) NOT NULL DEFAULT '0',"
//				+ "credits              int(10) NOT NULL DEFAULT '0',"
//				+ "notifysound          tinyint(1) NOT NULL DEFAULT '0',"
//				+ "timeoffset           char(4) NOT NULL DEFAULT '',"
//				+ "newpm                smallint(6) NOT NULL DEFAULT '0',"
//				+ "newprompt            smallint(6) NOT NULL DEFAULT '0',"
//				+ "accessmasks          tinyint(1) NOT NULL DEFAULT '0',"
//				+ "allowadmincp         tinyint(1) NOT NULL DEFAULT '0',"
//				+ "onlyacceptfriendpm   tinyint(1) NOT NULL DEFAULT '0',"
//				+ "conisbind            tinyint(1) NOT NULL DEFAULT '0');");
        db.execSQL("CREATE TABLE IF NOT EXISTS forum_forum("
                + "fid                  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fup                  mediumint(8) NOT NULL DEFAULT '0',"
                + "type                 VARCHAR(10) NOT NULL DEFAULT 'forum',"
                + "name                 char(50) NOT NULL DEFAULT '',"
                + "status               tinyint(1) NOT NULL DEFAULT '0',"
                + "displayorder         smallint(6) NOT NULL DEFAULT '0',"
                + "styleid              smallint(6) NOT NULL DEFAULT '0',"
                + "threads              mediumint(8) NOT NULL DEFAULT '0',"
                + "posts                mediumint(8) NOT NULL DEFAULT '0',"
                + "todayposts           mediumint(8) NOT NULL DEFAULT '0',"
                + "yesterdayposts       mediumint(8) NOT NULL DEFAULT '0',"
                + "rank                 smallint(6) NOT NULL DEFAULT '0',"
                + "oldrank              smallint(6) NOT NULL DEFAULT '0',"
                + "lastpost             char(110) NOT NULL DEFAULT '',"
                + "fdomain              char(15) NOT NULL DEFAULT '',"
                + "allowsmilies         tinyint(1) NOT NULL DEFAULT '0',"
                + "allowhtml            tinyint(1) NOT NULL DEFAULT '0',"
                + "allowbbcode          tinyint(1) NOT NULL DEFAULT '0',"
                + "allowimgcode         tinyint(1) NOT NULL DEFAULT '0',"
                + "allowmediacode       tinyint(1) NOT NULL DEFAULT '0',"
                + "allowanonymous       tinyint(1) NOT NULL DEFAULT '0',"
                + "allowpostspecial     smallint(6) NOT NULL DEFAULT '0',"
                + "allowspecialonly     tinyint(1) NOT NULL DEFAULT '0',"
                + "allowappend          tinyint(1) NOT NULL DEFAULT '0',"
                + "alloweditrules       tinyint(1) NOT NULL DEFAULT '0',"
                + "allowfeed            tinyint(1) NOT NULL DEFAULT '1',"
                + "allowside            tinyint(1) NOT NULL DEFAULT '0',"
                + "recyclebin           tinyint(1) NOT NULL DEFAULT '0',"
                + "modnewposts          tinyint(1) NOT NULL DEFAULT '0',"
                + "jammer               tinyint(1) NOT NULL DEFAULT '0',"
                + "disablewatermark     tinyint(1) NOT NULL DEFAULT '0',"
                + "inheritedmod         tinyint(1) NOT NULL DEFAULT '0',"
                + "autoclose            smallint(6) NOT NULL DEFAULT '0',"
                + "forumcolumns         tinyint(3) NOT NULL DEFAULT '0',"
                + "catforumcolumns      tinyint(3) NOT NULL DEFAULT '0',"
                + "threadcaches         tinyint(1) NOT NULL DEFAULT '0',"
                + "alloweditpost        tinyint(1) NOT NULL DEFAULT '1',"
                + "simple               tinyint(1) NOT NULL DEFAULT '0',"
                + "modworks             tinyint(1) NOT NULL DEFAULT '0',"
                + "allowglobalstick     tinyint(1) NOT NULL DEFAULT '1',"
                + "level                smallint(6) NOT NULL DEFAULT '0',"
                + "commoncredits        int(10) NOT NULL DEFAULT '0',"
                + "archive              tinyint(1) NOT NULL DEFAULT '0',"
                + "recommend            smallint(6) NOT NULL DEFAULT '0',"
                + "favtimes             mediumint(8) NOT NULL DEFAULT '0',"
                + "sharetimes           mediumint(8) NOT NULL DEFAULT '0',"
                + "disablethumb         tinyint(1) NOT NULL DEFAULT '0',"
                + "disablecollect       tinyint(1) NOT NULL DEFAULT '0');");
        db.execSQL("CREATE TABLE IF NOT EXISTS forum_post("
                + "pid                  int(10) NOT NULL PRIMARY KEY,"
                + "fid                  mediumint(8) NOT NULL DEFAULT '0',"
                + "tid                  mediumint(8) NOT NULL DEFAULT '0',"
                + "first                tinyint(1) NOT NULL DEFAULT '0',"
                + "author               varchar(15) NOT NULL DEFAULT '',"
                + "authorid             mediumint(8) NOT NULL DEFAULT '0',"
                + "subject              varchar(80) NOT NULL DEFAULT '',"
                + "dateline             int(10) NOT NULL DEFAULT '0',"
                + "message              mediumtext NOT NULL,"
                + "useip                varchar(15) NOT NULL DEFAULT '',"
                + "invisible            tinyint(1) NOT NULL DEFAULT '0',"
                + "anonymous            tinyint(1) NOT NULL DEFAULT '0',"
                + "usesig               tinyint(1) NOT NULL DEFAULT '0',"
                + "htmlon               tinyint(1) NOT NULL DEFAULT '0',"
                + "bbcodeoff            tinyint(1) NOT NULL DEFAULT '0',"
                + "smileyoff            tinyint(1) NOT NULL DEFAULT '0',"
                + "parseurloff          tinyint(1) NOT NULL DEFAULT '0',"
                + "attachment           tinyint(1) NOT NULL DEFAULT '0',"
                + "rate                 smallint(6) NOT NULL DEFAULT '0',"
                + "ratetimes            tinyint(3) NOT NULL DEFAULT '0',"
                + "status               int(10) NOT NULL DEFAULT '0',"
                + "tags                 varchar(255) NOT NULL DEFAULT '0',"
                + "COMMENT              tinyint(1) NOT NULL DEFAULT '0',"
                + "replycredit          int(10) NOT NULL DEFAULT '0',"
                + "position             INTEGER);");/* 这里本来不是primary key的，我修改了 */
        db.execSQL("CREATE TABLE IF NOT EXISTS forum_thread("
                + "tid                  INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + "fid                  mediumint(8) NOT NULL DEFAULT '0',"
                + "posttableid          smallint(6) NOT NULL DEFAULT '0',"
                + "sortid               smallint(6) NOT NULL DEFAULT '0',"
                + "readperm             tinyint(3) NOT NULL DEFAULT '0',"
                + "price                smallint(6) NOT NULL DEFAULT '0',"
                + "author               char(15) NOT NULL DEFAULT '',"
                + "authorid             mediumint(8) NOT NULL DEFAULT '0',"
                + "subject              char(80) NOT NULL DEFAULT '',"
                + "dateline             int(10) NOT NULL DEFAULT '0',"
                + "lastpost             int(10) NOT NULL DEFAULT '0',"
                + "lastposter           char(15) NOT NULL DEFAULT '',"
                + "views                int(10) NOT NULL DEFAULT '0',"
                + "replies              mediumint(8) NOT NULL DEFAULT '0',"
                + "displayorder         tinyint(1) NOT NULL DEFAULT '0',"
                + "highlight            tinyint(1) NOT NULL DEFAULT '0',"
                + "digest               tinyint(1) NOT NULL DEFAULT '0',"
                + "rate                 tinyint(1) NOT NULL DEFAULT '0',"
                + "special              tinyint(1) NOT NULL DEFAULT '0',"
                + "attachment           tinyint(1) NOT NULL DEFAULT '0',"
                + "moderated            tinyint(1) NOT NULL DEFAULT '0',"
                + "closed               mediumint(8) NOT NULL DEFAULT '0',"
                + "stickreply           tinyint(1) NOT NULL DEFAULT '0',"
                + "recommends           smallint(6) NOT NULL DEFAULT '0',"
                + "recommend_add        smallint(6) NOT NULL DEFAULT '0',"
                + "recommend_sub        smallint(6) NOT NULL DEFAULT '0',"
                + "heats                int(10) NOT NULL DEFAULT '0',"
                + "status               smallint(6) NOT NULL DEFAULT '0',"
                + "isgroup              tinyint(1) NOT NULL DEFAULT '0',"
                + "favtimes             mediumint(8) NOT NULL DEFAULT '0',"
                + "sharetimes           mediumint(8) NOT NULL DEFAULT '0',"
                + "stamp                tinyint(3) NOT NULL DEFAULT '-1',"
                + "icon                 tinyint(3) NOT NULL DEFAULT '-1',"
                + "pushedaid            mediumint(8) NOT NULL DEFAULT '0',"
                + "cover                smallint(6) NOT NULL DEFAULT '0',"
                + "replycredit          smallint(6) NOT NULL DEFAULT '0',"
                + "relatebytag          char(255) NOT NULL DEFAULT '0',"
                + "maxposition          int(8) NOT NULL DEFAULT '0',"
                + "bgcolor              char(8) NOT NULL DEFAULT '',"
                + "comments             int(10) NOT NULL DEFAULT '0',"
                + "logopath              CHAR(18) NULL,"
                + "avatarurl              CHAR(18) NULL,"
                + "firstcontent              mediumtext NOT NULL,"
                + "donaCount            SMALLINT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS threadGoods("
                + "gID                  bigint NOT NULL,"
                + "orderID              CHAR(20) NULL,"
                + "descr                CHAR(100) NULL,"
                + "score                TINYINT NULL,"
                + "respTxt              CHAR(100) NULL,"
                + "dealTime             DATETIME NULL,"
                + "endTime              DATETIME NULL,"
                + "delMethod            TINYINT NULL,"
                + "status               TINYINT NOT NULL,"
                + "dealType             SMALLINT NULL,"
                + "donator              BIGINT NULL,"
                + "receiver             BIGINT NULL,"
                + "addrID               CHAR(18) NULL,"
                + "gsID                 INTEGER NULL,"
                + "delFeeType           CHAR(1) NOT NULL,"
                + "tid                  mediumint(8) NOT NULL,"
                + "PRIMARY KEY (tid,gID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log.e("DBOpenHelper", "onUpgradeonUpgradeonUpgradeonUpgrade");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS chatrec");
        db.execSQL("DROP TABLE IF EXISTS chatlist");
        db.execSQL("DROP TABLE IF EXISTS goods");
        db.execSQL("DROP TABLE IF EXISTS goodsPara");
        db.execSQL("DROP TABLE IF EXISTS goodsSite");
        db.execSQL("DROP TABLE IF EXISTS mailAddress");
        db.execSQL("DROP TABLE IF EXISTS markThread");
        db.execSQL("DROP TABLE IF EXISTS paras");
        db.execSQL("DROP TABLE IF EXISTS postAttachments");
        db.execSQL("DROP TABLE IF EXISTS common_focus");
        db.execSQL("DROP TABLE IF EXISTS common_member");
        db.execSQL("DROP TABLE IF EXISTS forum_forum");
        db.execSQL("DROP TABLE IF EXISTS forum_post");
        db.execSQL("DROP TABLE IF EXISTS forum_thread");
        db.execSQL("DROP TABLE IF EXISTS threadGoods");
        onCreate(db);

    }
    //知道某一条记录是否存在
    public static Boolean isExit(SQLiteDatabase db,String uid,int focusid){
        Boolean exit=false;
        String sql = "select* from common_focus where uid=? and focusID=? COLLATE NOCASE";// 插入操作的SQL语句
        Cursor c = db.rawQuery(sql, new String[] { String.valueOf(uid),String.valueOf(focusid)});
        if (c != null) {
            if (c.moveToFirst()) {
                exit=true;
            }
        }
        return exit;
    }

    // 插入聊天记录
    public static void addChatRecord(SQLiteDatabase db,MessageEntity message) {
        String chatfrom, chatto, msg, date_str;
        long date;
        int type,chatto_id;
        chatfrom = message.chatfrom;
        chatto_id=message.chatto_id;
        chatto = message.chatto;
        type=message.type;
        msg = message.msg;
        date = message.date;
        date_str = String.valueOf(date);
        String sql = "insert or replace into chatrec(chatFrom,chatto_id,chatTo,chatDate,type,chatContent) values(?,?,?,?,?,?)";
        db.execSQL(sql, new Object[] { chatfrom,chatto_id, chatto, date_str,
                type,msg });
    }

    // 获取聊天记录
    public static List<MessageEntity> readChatRec(SQLiteDatabase db,
                                                  String loginuser,int chatto_id) {
        List<MessageEntity> records = new ArrayList<MessageEntity>();
        String sql = "select* from chatrec where chatFrom=? and chatto_id="+chatto_id+" COLLATE NOCASE";// 插入操作的SQL语句
        String chatfrom = "", chatto = "", msg = "", date_str = "";
        long date = 0;
        int type = 0;
        Cursor c = db.rawQuery(sql, new String[] { loginuser});
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    MessageEntity record = new MessageEntity(chatfrom,chatto_id,chatto,
                            date, msg, type);
                    chatfrom = c.getString(c.getColumnIndex("chatFrom"));
//					chatto_id = c.getString(c.getColumnIndex("chatto_id"));
                    chatto = c.getString(c.getColumnIndex("chatTo"));
                    date = c.getLong(c.getColumnIndex("chatDate"));
                    msg = c.getString(c.getColumnIndex("chatContent"));
//					if (chatfrom.equals(loginuser)) {
//						type = 1;
//					} else {
//						type = 0;
//					}
                    type = c.getInt(c.getColumnIndex("type"));
                    record.chatfrom = chatfrom;
                    record.chatto_id=chatto_id;
                    record.chatto = chatto;
                    record.date = date;
                    record.msg = msg;
                    record.type = type;
                    records.add(record);
                } while (c.moveToNext());
            }
        }
        return records;
    }
    //聊过天的添加到列表里
    public static void addChatList(SQLiteDatabase db,String loginuser,int chatto_id,String chatto_avatar){
        String sql = "insert or replace into chatlist(chatFrom,chatto_id,chatto_avatar) values(?,?,?)";
        db.execSQL(sql, new Object[] { loginuser,chatto_id,chatto_avatar});
    }
    //获取聊过天的关注人
    public static List<ChatListEntity> getChatID(SQLiteDatabase db,String loginuser){
        List<ChatListEntity> chatlists = new ArrayList<ChatListEntity>();
        String last_msg = null,chatto_id=null,chatTo=null,chatto_avatar="";
        long date = 0;
        String sql = "select* from chatlist where chatFrom=? COLLATE NOCASE";
        Cursor c = db.rawQuery(sql, new String[] { loginuser});
        if (c != null) {
            if (c.moveToFirst()) {
                do{
                    ChatListEntity chatlist = new ChatListEntity(loginuser,chatto_id,chatTo,  last_msg,  date,chatto_avatar);
//					userID=loginuser;
                    chatto_id = c.getString(c.getColumnIndex("chatto_id"));
                    chatto_avatar=c.getString(c.getColumnIndex("chatto_avatar"));
//					last_msg = c.getString(c.getColumnIndex("chatContent"));
//					date = c.getLong(c.getColumnIndex("chatDate"));
//					chatlist.userID=userID;
                    chatlist.chatto_id=chatto_id;
                    chatlist.avatar=chatto_avatar;
//					chatlist.last_msg=last_msg;
//					chatlist.date=date;
                    chatlists.add(chatlist);
                } while (c.moveToNext());

            }
        }
        return chatlists;
    }
    //获取聊天列表
    public static List<ChatListEntity> getChatLists(SQLiteDatabase db,int i,List<ChatListEntity> chatlists){
//		String[] chatlists = null;
//		int i=0;
//		String sql1 = "select chatto_id from chatlist where chatFrom=?";
//		Cursor c1 = db.rawQuery(sql1, new String[] { loginuser});
//		if (c1 != null) {
//			if (c1.moveToFirst()) {
//				do {
//					chatlists[i]=c1.getString(c1.getColumnIndex("chatto_id"));
//					i++;
//				} while (c1.moveToNext());
//			}
//		}
//		List<ChatListEntity> chatlists = new ArrayList<ChatListEntity>();
        String userID = null,chatTo=null,chatto_id = null, last_msg = null;
        long date = 0;
        String sql = "select* from chatrec where chatFrom=? and chatto_id=? COLLATE NOCASE";
        Cursor c = db.rawQuery(sql, new String[] { chatlists.get(i).getUserID(),chatlists.get(i).getChatTo_ID()});
        if (c != null) {
            if (c.moveToLast()) {
//				do{
//					ChatListEntity chatlist = new ChatListEntity(userID,chatto_id,  last_msg,  date);
//					userID=loginuser;
//					chatto_id = c.getString(c.getColumnIndex("chatto_id"));
                chatTo = c.getString(c.getColumnIndex("chatTo"));
                last_msg = c.getString(c.getColumnIndex("chatContent"));
                date = c.getLong(c.getColumnIndex("chatDate"));
//					chatlist.userID=userID;
//					chatlists.get(i).chatto_id=chatto_id;
                chatlists.get(i).chatTo=chatTo;
                chatlists.get(i).last_msg=last_msg;
                chatlists.get(i).date=date;
//					chatlists.add(chatlist);
//				} while (c.moveToNext());

            }
        }
        return chatlists;
    }
    public static void addUser(SQLiteDatabase db,MemberEntity member) {
        String username,password,avatarurl;
        int uid;
        uid=member.getUid();
        username=member.getUsername();
        password=member.getPassword();
        avatarurl=member.getAvatarurl();
        String sql = "insert or replace into users(userID,userName,password,userAvatar) values(?,?,?,?)";
        db.execSQL(sql, new Object[] {uid,username,password,avatarurl});

    }
    public static void addThread(SQLiteDatabase db,ForumThreadExtendEntity thread) {
        //少了dateline
        String subject,author,avatarurl,firstimg,firstcontent;
        int tid,fid,authorid;
        tid=thread.getTid();
//		 fid=thread.getFid();
        authorid=thread.getAuthorid();
        author=thread.getAuthor();
        subject=thread.getSubject();
        avatarurl=thread.getAvatarurl();
        firstimg=thread.getFirstimg();
        firstcontent=thread.getFirstcontent();

        String sql = "insert or replace into forum_thread(tid,author,authorid,subject,logopath,avatarurl,firstcontent) "
                + "values(?,?,?,?,?,?,?)";
        db.execSQL(sql, new Object[] {tid,author,authorid,subject,firstimg,avatarurl,firstcontent});

    }
    public static List<ForumThreadExtendEntity> getThread(SQLiteDatabase db) {
        List<ForumThreadExtendEntity> threads = new ArrayList<ForumThreadExtendEntity>();
        String sql = "select* from forum_thread";// 插入操作的SQL语句
        String subject,author,avatarurl,firstimg,firstcontent;
        int tid,fid,authorid;
        Cursor c = db.rawQuery(sql, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    ForumThreadExtendEntity thread = new ForumThreadExtendEntity();
                    tid = c.getInt(c.getColumnIndex("tid"));
//					fid = c.getInt(c.getColumnIndex("fid"));
                    authorid = c.getInt(c.getColumnIndex("authorid"));
                    subject = c.getString(c.getColumnIndex("subject"));
                    author = c.getString(c.getColumnIndex("author"));
                    avatarurl = c.getString(c.getColumnIndex("avatarurl"));
                    firstimg = c.getString(c.getColumnIndex("logopath"));
                    firstcontent = c.getString(c.getColumnIndex("firstcontent"));

                    thread.setTid(tid);
//					thread.setFid(fid);
                    thread.setAuthorid(authorid);
                    thread.setSubject(subject);
                    thread.setAuthor(author);
                    thread.setAvatarurl(avatarurl);
                    thread.setFirstimg(firstimg);
                    thread.setFirstcontent(firstcontent);
                    threads.add(thread);
                } while (c.moveToNext());
            }
        }
        return threads;
    }
    //得到登陆过的用户头像url
    public static String getAvatarUrl(SQLiteDatabase db,String username){
        String userAvatar = "";
        Cursor c = db.rawQuery("select * from users where userName=? COLLATE NOCASE",new String[]{username});
        if(c.moveToFirst()) {
            userAvatar = c.getString(c.getColumnIndex("userAvatar"));
        }
        return userAvatar;
    }
    //更新用户头像url
    public static void updateAvatarUrl(SQLiteDatabase db,String username,String avatarurl){
//		String sql = "insert or replace into chatlist(chatFrom,chatto_id) values(?,?)";
//		db.execSQL(sql, new Object[] { loginuser,chatto_id});
        String sql ="update users set userAvatar =? where userName=?";
        db.execSQL(sql, new Object[] {avatarurl,username});
    }
    // 插入关注的人
    public static void addFocus(SQLiteDatabase db) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(i==j){

                }else{
                    String sql = "insert or replace into common_focus(uid,focusname,focusID,focusTime,initial,fType) values(?,?,?,?,?,?)";
                    db.execSQL(
                            sql,
                            new Object[] { i, "lee" + j, j,
                                    System.currentTimeMillis(),
                                    GetSpell.getFirstSpell("lee" + j), 0 });
                }
            }
        }
    }
    //取消关注
    public static void delFocus(SQLiteDatabase db,String uid,int focusid) {
        String sql="delete from common_focus where uid=? and focusID=?";
        db.execSQL(sql,new Object[]{uid,focusid});
    }
    // 插入用户
//	public static void addUser_Test(SQLiteDatabase db) {// ,UserEntitiy user
//	// String userName,userAvatar;
//	// userName=user.userName;
//	// userAvatar=user.userAvatar;
//		for (int i = 0; i < 10; i++) {
//			String sql = "insert or replace into pre_common_member(uid,username,password,initial) values(?,?,?,?)";
//			db.execSQL(sql, new Object[] { i, "lee" + i, "123456",GetSpell.getFirstSpell("lee" + i)});
//		}
//	}
//
//	// 插入关注的人
//	public static void addFocus_Test(SQLiteDatabase db) {
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				if(i==j){
//					
//				}else{
//					String sql = "insert or replace into pre_common_focus(uid,focusname,focusID,focusTime,initial,fType) values(?,?,?,?,?,?)";
//					db.execSQL(
//							sql,
//							new Object[] { i, "lee" + j, j,
//									System.currentTimeMillis(),
//									GetSpell.getFirstSpell("lee" + j), 0 });
//				}
//			}
//		}
//	}

    // 获取关注列表索引
    public static AlphabetIndexer getFocusIndexer(SQLiteDatabase db, int userid,String alphabet) {
        AlphabetIndexer indexer = null;
        String sql = "select focusname,initial from common_focus where uid="+userid+" COLLATE NOCASE";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            indexer = new AlphabetIndexer(
                    cursor,
                    cursor.getColumnIndex("initial"),
                    alphabet);
        }
        return indexer;
    }
    // 获取关注列表，返回关注对象
    public static List<FocusEntity> getFocus(SQLiteDatabase db, String uid) {
        List<FocusEntity> friends = new ArrayList<FocusEntity>();
        String sql = "select* from common_focus where uid=? COLLATE NOCASE";// 插入操作的SQL语句
        String focusname="";long focusTime = 0;
        int fType = 0,focusID=0;
        String initial = "";
        Cursor c = db.rawQuery(sql, new String[]{uid});
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    FocusEntity friend = new FocusEntity(focusname, focusID,focusTime,initial,fType);
                    focusname = c.getString(c.getColumnIndex("focusname"));
                    focusID = c.getInt(c.getColumnIndex("focusID"));
                    focusTime = c.getLong(c.getColumnIndex("focusTime"));
                    initial =c.getString(c.getColumnIndex("initial"));
                    fType = c.getInt(c.getColumnIndex("fType"));
//					friend.uid = uid;
                    friend.focusname = focusname;
                    friend.focusID =focusID;
                    friend.focusTime = focusTime;
                    friend.initial=initial;
                    friend.fType=fType;
                    friends.add(friend);
                } while (c.moveToNext());
            }
        }
        return friends;
    }
    // 获取用户列表
    public static List<MemberEntity> getMembers(SQLiteDatabase db) {
        List<MemberEntity> members = new ArrayList<MemberEntity>();
        String sql = "select* from users";// 插入操作的SQL语句
        String username="",password="", avatarurl = "";
        int uid = 0;
        Cursor c = db.rawQuery(sql, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    MemberEntity member = new MemberEntity();
                    uid = c.getInt(c.getColumnIndex("userID"));
                    username = c.getString(c.getColumnIndex("userName"));
                    avatarurl = c.getString(c.getColumnIndex("userAvatar"));
                    password = c.getString(c.getColumnIndex("password"));
                    member.setUid(uid);
                    member.setUsername(username);
                    member.setPassword(password);
                    member.setAvatarurl(avatarurl);
                    members.add(member);
                } while (c.moveToNext());
            }
        }
        return members;
    }
}