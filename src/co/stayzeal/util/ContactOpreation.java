package co.stayzeal.util;

import java.util.ArrayList;
import java.util.List;

import co.stayzeal.contact.model.ContactInfo;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

/**
 * 通过ContentResolver对Android 系统中的contact中的数据进行操作
 * 
 * @author YOUNG
 *
 */

public class ContactOpreation {

	private Context context;
	private ContentResolver contentResolver;
	private Uri uri;
	private List<ContactInfo> contactInfos;

	public ContactOpreation(Context context) {
		this.context = context;
		contentResolver = this.context.getContentResolver();
	}

	/**
	 * 获取所有的联系人信息
	 */
	public List<ContactInfo> getContactList() {

		uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor idCursor = contentResolver.query(uri, new String[] { "_id" },
				null, null, null);
		contactInfos = new ArrayList<ContactInfo>();
		ContactInfo contactInfo;
		Cursor dataCursor;
		while (idCursor.moveToNext()) {
			contactInfo = new ContactInfo();
			int id = idCursor.getInt(0);
			
			
			uri = Uri.parse("content://com.android.contacts/contacts/" + id
					+ "/data");
			dataCursor = contentResolver.query(uri, new String[] {
					"data1", "mimetype" }, null, null, null);
			contactInfo.setId(id);
			// 查询联系人表中的
			while (dataCursor.moveToNext()) {
				String data = dataCursor.getString(0);
				String type = dataCursor.getString(1);
				if ("vnd.android.cursor.item/name".equals(type)) {
					contactInfo.setContactName(data);
				} else if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					contactInfo.setContactNumber(data);
				} else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					contactInfo.setEamil(data);
				}
			}
			contactInfos.add(contactInfo);
			dataCursor.close();
		}
		return contactInfos;
	}
	
	/**
	 * 通过电话号码获取联系人信息
	 * @param phoneNumber
	 * @return
	 */
	public ContactInfo getContactByPhone(String phoneNumber) {
		
		ContactInfo contactInfo=new ContactInfo();
		uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phoneNumber);
		Cursor c = contentResolver.query(uri, new String[] { "display_name" },
				null, null, null);
		while (c.moveToNext()) {
			contactInfo.setContactName(c.getString(0));
			System.out.println("getContactByPhone(): "+c.getString(0));
		}
		
		return contactInfo;
	}
	
	
	/**
	 * 添加联系人信息
	 * @param contactInfo
	 */
	public void addContact(ContactInfo contactInfo){
		
		uri=Uri.parse("content://com.android.contacts/raw_contacts");  
		ContentValues values = new ContentValues();  
		// 向raw_contacts插入一条除了ID之外, 其他全部为NULL的记录, ID是自动生成的  
		long id = ContentUris.parseId(contentResolver.insert(uri, values));   
		//添加联系人姓名  
		uri = Uri.parse("content://com.android.contacts/data");  
		values.put("raw_contact_id", id);  
		values.put("data2", contactInfo.getContactName());  
		values.put("mimetype", "vnd.android.cursor.item/name");  
		contentResolver.insert(uri, values);  
		                //添加联系人电话  
		values.clear(); // 清空上次的数据  
		values.put("raw_contact_id", id);  
		values.put("data1", contactInfo.getContactNumber());  
		values.put("data2", "2");  
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");  
		contentResolver.insert(uri, values);  
		//添加联系人邮箱  
		values.clear();  
		values.put("raw_contact_id", id);  
		values.put("data1", contactInfo.getEamil());  
		values.put("data2", "1");  
		values.put("mimetype", "vnd.android.cursor.item/email_v2");  
		contentResolver.insert(uri, values); 
	}
	
	/**
	 * 通过事物添加联系人
	 * @param contactInfo
	 * @throws RemoteException
	 * @throws OperationApplicationException
	 */
	public void addContact2(ContactInfo contactInfo) throws RemoteException, OperationApplicationException{
		  
	    ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();  
	  
	    ContentProviderOperation operation1 = ContentProviderOperation //  
	            .newInsert(Uri.parse("content://com.android.contacts/raw_contacts")) //  
	            .withValue("_id", null) //  
	            .build();  
	    operations.add(operation1);  
	  
	    ContentProviderOperation operation2 = ContentProviderOperation //  
	            .newInsert(Uri.parse("content://com.android.contacts/data")) //  
	            .withValueBackReference("raw_contact_id", 0) //  
	            .withValue("data2", contactInfo.getContactName()) //  
	            .withValue("mimetype", "vnd.android.cursor.item/name") //  
	            .build();  
	    operations.add(operation2);  
	      
	    ContentProviderOperation operation3 = ContentProviderOperation //  
	            .newInsert(Uri.parse("content://com.android.contacts/data")) //  
	            .withValueBackReference("raw_contact_id", 0) //  
	            .withValue("data1", contactInfo.getContactNumber()) //  
	            .withValue("data2", "2") //  
	            .withValue("mimetype", "vnd.android.cursor.item/phone_v2") //  
	            .build();  
	    operations.add(operation3);  
	  
	    ContentProviderOperation operation4 = ContentProviderOperation //  
	            .newInsert(Uri.parse("content://com.android.contacts/data")) //  
	            .withValueBackReference("raw_contact_id", 0) //  
	            .withValue("data1", contactInfo.getEamil()) //  
	            .withValue("data2", "2") //  
	            .withValue("mimetype", "vnd.android.cursor.item/email_v2") //  
	            .build();  
	    operations.add(operation4);  
	  
	    // 在事务中对多个操作批量执行  
	    contentResolver.applyBatch("com.android.contacts", operations);
	}
	
	
}
