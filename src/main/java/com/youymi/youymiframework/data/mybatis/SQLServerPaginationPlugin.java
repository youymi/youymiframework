package com.youymi.youymiframework.data.mybatis;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.youymi.youymiframework.data.Pager;

public class SQLServerPaginationPlugin extends PluginAdapter{
	@Override  
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,  
            IntrospectedTable introspectedTable) {  
        // add field, getter, setter for limit clause  
        addPage(topLevelClass, introspectedTable, "page");  
        return super.modelExampleClassGenerated(topLevelClass,  
                introspectedTable);  
    }  
	
//	<sql id="sqlprefix" >
//	<if test="page != null" >
//	 select * from ( select ROW_NUMBER() over(order by 
//	 <if test="orderByClause != null" >
//	 ${orderByClause}
//	</if>
//	 <if test="orderByClause == null" >
//	 <include refid="Base_Column_List" />
//	</if>
//
//	  ) as rownum_, * from (
//	</if>
//	</sql>
//
//	<sql id="sqlsuffix" >   
//	    <if test="page != null" >
//	        <![CDATA[ ) a ) b  where  rownum_ > ${page.pageBegin} and rownum_ <= ${page.pageEnd}]]>
//	    </if>
//	</sql>
  
    @Override  
    public boolean sqlMapDocumentGenerated(Document document,  
            IntrospectedTable introspectedTable) {  
        XmlElement parentElement = document.getRootElement();  
  
        // 产生分页语句前半部分  
        XmlElement paginationPrefixElement = new XmlElement("sql");  
        paginationPrefixElement.addAttribute(new Attribute("id",  
                "SQLServerDialectPrefix"));  
        XmlElement pageStart = new XmlElement("if");  
        pageStart.addAttribute(new Attribute("test", "page != null"));  
        pageStart.addElement(new TextElement("select * from ( select ROW_NUMBER() over(order by "));
        
        XmlElement ifOrderByClase = new XmlElement("if");  
        ifOrderByClase.addAttribute(new Attribute("test", "orderByClause != null"));
        ifOrderByClase.addElement(new TextElement(" ${orderByClause} "));
        pageStart.addElement(ifOrderByClase);
        
        XmlElement ifNotOrderByClase = new XmlElement("if");  
        ifNotOrderByClase.addAttribute(new Attribute("test", "orderByClause == null"));  
        XmlElement includeBaseColoum = new XmlElement("include ");
        includeBaseColoum.addAttribute(new Attribute("refid", "Base_Column_List"));
        ifNotOrderByClase.addElement(includeBaseColoum);
        pageStart.addElement(ifNotOrderByClase);
       
        pageStart.addElement(new TextElement(" ) as rownum_, * from ( "));
        
        
        paginationPrefixElement.addElement(pageStart);  
        parentElement.addElement(paginationPrefixElement);  
  
        // 产生分页语句后半部分  
        XmlElement paginationSuffixElement = new XmlElement("sql");  
        paginationSuffixElement.addAttribute(new Attribute("id",  
                "SQLServerDialectSuffix"));  
        XmlElement pageEnd = new XmlElement("if");  
        pageEnd.addAttribute(new Attribute("test", "page != null"));  
        pageEnd.addElement(new TextElement(  
                "<![CDATA[ ) a ) b  where rownum_ > #{page.pageBegin} and rownum_ <= #{page.pageEnd} ]]>"));  
        paginationSuffixElement.addElement(pageEnd);  
        parentElement.addElement(paginationSuffixElement);  
  
        return super.sqlMapDocumentGenerated(document, introspectedTable);  
    }  
  
    @Override  
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(  
            XmlElement element, IntrospectedTable introspectedTable) {  
  
        XmlElement pageStart = new XmlElement("include"); //$NON-NLS-1$     
        pageStart.addAttribute(new Attribute("refid", "SQLServerDialectPrefix"));  
        element.getElements().add(0, pageStart);  
        
        // 去除 order by 语句
        element.getElements().remove(element.getElements().size()-1);
  
        XmlElement isNotNullElement = new XmlElement("include"); //$NON-NLS-1$     
        isNotNullElement.addAttribute(new Attribute("refid",  
                "SQLServerDialectSuffix"));  
        element.getElements().add(isNotNullElement);  
  
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,  
                introspectedTable);  
    }  
    
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable)
    {
      XmlElement pageStart = new XmlElement("include");
      pageStart.addAttribute(new Attribute("refid", "SQLServerDialectPrefix"));
      element.getElements().add(0, pageStart);
      
      // 去除 order by 语句
      element.getElements().remove(element.getElements().size()-1);
      
      XmlElement isNotNullElement = new XmlElement("include");
      isNotNullElement.addAttribute(new Attribute("refid", "SQLServerDialectSuffix"));
      element.getElements().add(isNotNullElement);
      
      return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }
  
    /** 
     * @param topLevelClass 
     * @param introspectedTable 
     * @param name 
     */  
    private void addPage(TopLevelClass topLevelClass,  
            IntrospectedTable introspectedTable, String name) {  
        topLevelClass.addImportedType(new FullyQualifiedJavaType(  
        		Pager.class.getName()));  
        CommentGenerator commentGenerator = context.getCommentGenerator();  
        Field field = new Field();  
        field.setVisibility(JavaVisibility.PROTECTED);  
        field.setType(new FullyQualifiedJavaType(
        		Pager.class.getName()
        		//"com.youymi.youymiframework.data.Pager"
        		));  
        field.setName(name);  
        commentGenerator.addFieldComment(field, introspectedTable);  
        topLevelClass.addField(field);  
        char c = name.charAt(0);  
        String camel = Character.toUpperCase(c) + name.substring(1);  
        Method method = new Method();  
        method.setVisibility(JavaVisibility.PUBLIC);  
        method.setName("set" + camel);  
        method.addParameter(new Parameter(new FullyQualifiedJavaType(  
        		Pager.class.getName()), name));  
        method.addBodyLine("this." + name + "=" + name + ";");  
        commentGenerator.addGeneralMethodComment(method, introspectedTable);  
        topLevelClass.addMethod(method);  
        method = new Method();  
        method.setVisibility(JavaVisibility.PUBLIC);  
        method.setReturnType(new FullyQualifiedJavaType(  
        		Pager.class.getName()));  
        method.setName("get" + camel);  
        method.addBodyLine("return " + name + ";");  
        commentGenerator.addGeneralMethodComment(method, introspectedTable);  
        topLevelClass.addMethod(method);  
    }  
  
    /** 
     * This plugin is always valid - no properties are required 
     */  
    public boolean validate(List<String> warnings) {  
        return true;  
    }  
}
