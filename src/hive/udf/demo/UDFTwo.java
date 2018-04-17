package hive.udf.demo;

import java.util.ArrayList;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.lazy.LazyMap;
import org.apache.hadoop.hive.serde2.lazy.LazyString;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector; 

public class UDFTwo extends GenericUDF{

	 ////输入变量定义  
    private ObjectInspector nameObj;  
    private ListObjectInspector listoi;  
    private MapObjectInspector mapOI;  
    private ArrayList<Object> valueList = new ArrayList<Object>();   
	@Override
	public Object evaluate(DeferredObject[] arguments) throws HiveException {
		// TODO 自动生成的方法存根
		LazyString LName = (LazyString)(arguments[0].get());  
	      String strName = ((StringObjectInspector)nameObj).getPrimitiveJavaObject( LName );  
	  
	      int nelements = listoi.getListLength(arguments[1].get());  
	          int nTotalScore=0;  
	          valueList.clear();  
	          //遍历list  
	      for(int i=0;i<nelements;i++)  
	      {   
	               LazyMap LMap = (LazyMap)listoi.getListElement(arguments[1].get(),i);  
	               //获取map中的所有value值  
	           valueList.addAll(mapOI.getMap(LMap).values());   
	               for (int j = 0; j < valueList.size(); j++)  
	           {  
	                   nTotalScore+=Integer.parseInt(valueList.get(j).toString());  
	               }                 
	          }  
	      Object[] e;     
	      e = new Object[2];  
	      e[0] = new Text(strName);  
	          e[1] = new IntWritable(nTotalScore);  
	          return e;  
	}

	@Override
	public String getDisplayString(String[] children) {
		// TODO 自动生成的方法存根
		StringBuilder sb = new StringBuilder();  
        sb.append("helloGenericUDFNew(");  
        sb.append(children[0]);  
        sb.append(")");  
 
        return sb.toString();
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
		// TODO 自动生成的方法存根
		nameObj = (ObjectInspector)arguments[0];  
        listoi = (ListObjectInspector)arguments[1];  
    mapOI = ((MapObjectInspector)listoi.getListElementObjectInspector());  
        //输出结构体定义  
        ArrayList<String> structFieldNames = new ArrayList<String>();  
        ArrayList<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();  
        structFieldNames.add("name");  
        structFieldNames.add("totalScore");  
 
        structFieldObjectInspectors.add( PrimitiveObjectInspectorFactory.writableStringObjectInspector );  
        structFieldObjectInspectors.add( PrimitiveObjectInspectorFactory.writableIntObjectInspector );  

        StructObjectInspector si2;  
        si2 = ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames, structFieldObjectInspectors);   
        return si2;  
	}

}
