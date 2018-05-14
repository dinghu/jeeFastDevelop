package com.xjj.framework.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

public class DocUtils {
	
	public static final Rectangle PAGE_SIZE_B5 = PageSize.B5;
	public static final Rectangle PAGE_SIZE_A4 = PageSize.A4;
	public static final Rectangle PAGE_SIZE_A3 = PageSize.A3;
	
	public static final Integer ALIGN_LEFT = Element.ALIGN_LEFT;
	public static final Integer ALIGN_RIGHT = Element.ALIGN_RIGHT;
	public static final Integer ALIGN_CENTER = Element.ALIGN_CENTER;
	
	public static final Integer FONT_STYLE_NORMAL = Font.NORMAL;
	public static final Integer FONT_STYLE_BOLD = Font.BOLD;
	public static final Integer FONT_STYLE_ITALIC = Font.ITALIC;
	
	private static final DocUtils instance = new DocUtils();

	
	private DocUtils(){}
	
	public static DocFile createPDF(Rectangle pageSize){
		return instance.new DocFile("pdf", pageSize);
	}
	
	public static DocFile createPDF(Rectangle pageSize, int marginLeft, int marginRight, int marginTop, int marginBottom){
		return instance.new DocFile("pdf", pageSize,marginLeft, marginRight, marginTop, marginBottom);
	}
	
	public static DocFile createRTF(Rectangle pageSize){
		return instance.new DocFile("rtf", pageSize);
	}
	
	public static HtmlPdfFile createHtmlPDF(){
		return instance.new HtmlPdfFile();
	}
	
	public static DocFile createFile(Rectangle pageSize,int marginLeft, int marginRight, int marginTop, int marginBottom){
		return instance.new DocFile("rtf", pageSize, marginLeft, marginRight, marginTop, marginBottom);
	}

	public static TextStyle createTextStyle(){
		return instance.new TextStyle();
	}

	public static TableStyle createTableStyle(){
		return instance.new TableStyle();
	}
	
	public static void main(String[] args) {
		try {
//			List<Link> links = new ArrayList<Link>();
//			Link link = new Link();
//			link.setTitle("aaa");
//			link.setUrl("f:/back.gif");
//			links.add(link);
//			link = new Link();
//			link.setTitle("ccc");
//			link.setUrl("f:/back.gif");
//			links.add(link);
//			link = new Link();
//			link.setTitle("eee");
//			link.setUrl("f:/back.gif");
//			links.add(link);
//			file.addTable(links, "text(title):����;img(url?f:/),text(title),text(url):��ַ",null,null,null);
			
			//file.addImage("f:/banner.jpg",null);
			
//			DocFile file = DocUtils.createRTF(DocUtils.PAGE_SIZE_B5);
//			file.addText("aaaaa", DocUtils.createTextStyle().setAlign(DocUtils.ALIGN_CENTER).setFontSize(20));
//			file.addText("bbbbb", null);
//			file.addText("中文", null);
//			file.writeFile("d:/rtf.rtf");
//			
//			DocFile file1 = DocUtils.createPDF(DocUtils.PAGE_SIZE_B5);
//			file1.addText("aaaaa", DocUtils.createTextStyle().setAlign(DocUtils.ALIGN_CENTER).setFontSize(20));
//			file1.addText("bbbbb", null);
//			file1.addText("中文", null);
//			file1.writeFile("d:/pdf.pdf");

			HtmlPdfFile aa = instance.new HtmlPdfFile();
			aa.htmlCodeToPDF("<b>中文</b>", "d:/html.pdf", true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class DocFile{
	
		private String docType = "pdf";
		private Rectangle pageSize = PageSize.A4;
		private int marginLeft = 40; 
		private int marginRight = 40;
		private int marginTop = 40;
		private int marginBottom = 40;
		private List<DocElement> contexts = new ArrayList<DocElement>();//�ĵ��б��������
		
		public DocFile(String docType, Rectangle pageSize){
			if("rtf".equals(docType)){
				this.docType = "rtf";
			}else{
				this.docType = "pdf";
			}
			setPageSize(pageSize);
		}
		public DocFile(String docType, Rectangle pageSize,int marginLeft, int marginRight, int marginTop, int marginBottom){
			if("rtf".equals(docType)){
				this.docType = "rtf";
			}else{
				this.docType = "pdf";
			}
			setPageSize(pageSize);
			this.marginLeft = marginLeft;
			this.marginRight = marginRight;
			this.marginTop = marginTop;
			this.marginBottom = marginBottom;
		}

		public Rectangle getPageSize() {
			return pageSize;
		}
		public void setPageSize(Rectangle pageSize) {
			this.pageSize = pageSize;
		}
		public int getMarginLeft() {
			return marginLeft;
		}
		public void setMarginLeft(int marginLeft) {
			this.marginLeft = marginLeft;
		}
		public int getMarginRight() {
			return marginRight;
		}
		public void setMarginRight(int marginRight) {
			this.marginRight = marginRight;
		}
		public int getMarginTop() {
			return marginTop;
		}
		public void setMarginTop(int marginTop) {
			this.marginTop = marginTop;
		}
		public int getMarginBottom() {
			return marginBottom;
		}
		public void setMarginBottom(int marginBottom) {
			this.marginBottom = marginBottom;
		}
		
		public Chunk getString(String content,TextStyle style){
			if(style == null){
				style = new TextStyle();
			}
			Chunk ck = new Chunk(content); 
			Font font = new Font(style.getFontType(), style.getFontSize(), style.getFontStyle());
			ck.setFont(font);
			return ck;
		}
		public DocFile addString(String content,TextStyle style){
			this.contexts.add(new DocElement(DocElement.STRING, getString(content,style)));
			return this;
		}
		/**
		 * 获得文本����ı�
		 * @param content
		 * @param style
		 * @return
		 */
		public Paragraph getText(String content,TextStyle style){
			if(style == null){
				style = new TextStyle();
			}
			Font font = new Font(style.getFontType(), style.getFontSize(), style.getFontStyle());
			Paragraph text = new Paragraph(content,font);
			text.setAlignment(style.getAlign());
			text.setFont(font);
			text.setSpacingBefore(style.getSpaceLine() * style.getFontSize());
			text.setFirstLineIndent(style.getFirstIndent() * style.getFontSize());
			return text;
		}
		
		/**
		 * 添加文本����ı�
		 * @param content
		 * @param style
		 * @return
		 */
		public DocFile addText(String content,TextStyle style){
			this.contexts.add(new DocElement(DocElement.PARAGRAPH, getText(content,style)));
			return this;
		}
		
		/**
		 * 获得表格
		 * @param data 数据列表，通过反射自动获得相应的数据�����
		 * @param descriptor text(method1?align&size&style):title1;text(method2?align&size&style),text(method3?align&size&style),text(method4.method5?align&size&style):title2;img(method6?path&align$&width&height):title3
		 * @return
		 */
		public Table getTable(List<?> data,String descriptor, TextStyle cellStyle,TextStyle headStyle, TableStyle tableStyle){
			if(data == null || data.isEmpty() || descriptor == null || descriptor.equals("")){
				return null;
			}
			
			//检测并复制样式��Ⲣ������ʽ
			if(tableStyle==null){
				tableStyle = new TableStyle();
			}
			if(cellStyle==null){
				cellStyle = new TextStyle();
			}
			if(headStyle == null){
				headStyle = cellStyle;
			}else{
				if(headStyle.getFontType()==null){headStyle.setFontType(cellStyle.getFontType());}
				if(headStyle.getFontSize()==null){headStyle.setFontSize( cellStyle.getFontSize());}
				if(headStyle.getFontStyle()==null){headStyle.setFontStyle( cellStyle.getFontStyle());}
				if(headStyle.getAlign()==null){headStyle.setAlign( cellStyle.getAlign());}
				if(headStyle.getSpaceLine()==null){headStyle.setSpaceLine( cellStyle.getSpaceLine());}
				if(headStyle.getFirstIndent()==null){headStyle.setFirstIndent( cellStyle.getFirstIndent());}
				if(headStyle.getWidth()==null){headStyle.setWidth( cellStyle.getWidth());}
				if(headStyle.getHeight()==null){headStyle.setHeight( cellStyle.getHeight());}
			}
			
			List<String> headList = new ArrayList<String>();
			Map<String,TableGrid> gridMap = new HashMap<String,TableGrid>();

			
	        String[] grids = descriptor.split(";");
	        int pos;
	      //得到所有的表格头信息
	        for (String grid : grids) {
	        	pos = grid.lastIndexOf(":");
	            if(pos>0){
	            	String headValue = grid.substring(0,pos);
	            	String head = grid.substring(pos+1);
	            	headList.add(head);
	            	
	            	TableGrid tableGrid = new TableGrid(head);
	            	
	            	//如果一个表格含有多个内容，分割开
	            	String[] methods = headValue.split(",");
	            	for(String method : methods){
	            		pos = method.indexOf("(");
	            		String method_type = method.substring(0,pos);
	            		String method_param = method.substring(pos+1,method.length()-1);
	            		
	            		//设定类型 text img
	            		int type = 1;
	            		if(method_type.equalsIgnoreCase("text")){
	            			type = 1;
	            		}else if(method_type.equalsIgnoreCase("img")){
	            			type = 2;
	            		}
	            		
	            		pos = method_param.indexOf("?");
	            		String obj_method;
	            		TextStyle style = new TextStyle();
	            		if(pos > 0){
	            			obj_method = method_param.substring(0,pos);
	            			//String obj_param = method_param.substring(pos+1);
	            			//设定style
	            		}else{
	            			obj_method = method_param;
	            		}
	            		tableGrid.addElement(new TableElement(type,obj_method,style));
	            	}
	            	gridMap.put(head, tableGrid);
	            }
	        }
	        if(headList.isEmpty()){
	        	return null;
	        }
	        
	        Table table = null;
	        
			try{
				table = new Table(headList.size());  
				
		        table.setBorderWidth(tableStyle.getBorderWidth());   
		        table.setBorderColor(tableStyle.getBoderColor());   
		        table.setPadding(tableStyle.getPadding());   
		        table.setSpacing(tableStyle.getSpacing());   
	
		      //添加表头元素�ͷԪ��
		        Cell cell;
		        for(String head : headList){
		        	cell = new Cell(getText(head,headStyle));
			        cell.setHeader(true);
			        table.addCell(cell);
		        }
		        table.endHeaders();//表头结束   ��ͷ����   
		           
		      //表格主体�������
		        for(Object object : data){
		        	for(String head : headList){
		        		cell = new Cell();
		        		boolean first_in = true;
		        		for(TableElement element : gridMap.get(head).elements){
		        			if(element.getType()==1){
		        				Paragraph p = getText(ReflectUtils.getValue(object,element.getValue()).toString(), element.getStyle());
		        				cell.addElement(p);
		        				//cell.addElement(this.getString(getValue(object,element.getValue()).toString()));
		        			}else if(element.getType()==2){
		        				//System.out.println(getValue(object,element.getValue()).toString());
			    				Image img = getImage(ReflectUtils.getValue(object,element.getValue()).toString(), element.getStyle());
		        				Chunk ck = new Chunk(img, 0, 0); 
			    				Paragraph p = new Paragraph(ck);
			    				p.setAlignment(element.getStyle().getAlign());
		        				cell.addElement(p);
		        			}
		        			if(first_in){
		        				cell.addElement(getText(tableStyle.getSplit(),null));
		        				first_in = false;
		        			}
			        	}
		        		table.addCell(cell);
		        	}
		        	
		        }
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return table;
		}
		
		/**
		 * 加入表格
		 * @param data 数据列表，通过反射自动获得相应的数据�����
		 * @param descriptor text(method1?align&size&style):title1;text(method2?align&size&style),text(method3?align&size&style),text(method4.method5?align&size&style):title2;img(method6?path&align$&width&height):title3
		 * @return
		 */
		public DocFile addTable(List<?> data,String descriptor, TextStyle cellStyle,TextStyle headStyle, TableStyle tableStyle){
			
			Table table = this.getTable(data, descriptor, cellStyle, headStyle, tableStyle);
			if(table != null){
				contexts.add(new DocElement(DocElement.TABLE,table));
			}
			return this;

		}

		/**
		 * 加入表格
		 * @param data 数据列表，通过反射自动获得相应的数据�����
		 * @param descriptor text(method1?align&size&style):title1;text(method2?align&size&style),text(method3?align&size&style),text(method4.method5?align&size&style):title2;img(method6?path&align$&width&height):title3
		 * @return
		 */
		public DocFile addTable(Table table){
			
			if(table != null){
				contexts.add(new DocElement(DocElement.TABLE,table));
			}
			return this;

		}
		
		/**
		 * 获得图片
		 * @param filePath
		 * @param style
		 * @return
		 */
		public Image getImage(String filePath,TextStyle style){
			Image img = null;
			if(style == null){
				style = new TextStyle();
			}
			try{
				if(style.getBasePath() != null && !style.getBasePath().equals("")){
					filePath = style.getBasePath() + filePath;
				}
				img = Image.getInstance(filePath);
				
				if(style.getWidth() > 0){
					img.scaleAbsoluteWidth(style.getWidth());
				}
				if(style.getHeight() >0 ){
					img.scaleAbsoluteHeight(style.getHeight());
				}
				img.setAlignment(style.getAlign());
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return img;
		}
		/**
		 * 添加图片
		 * @param filePath
		 * @param style
		 * @return
		 */
		public DocFile addImage(String filePath,TextStyle style){
			Image img = this.getImage(filePath, style);
			if(img != null){
				contexts.add(new DocElement(DocElement.IMAGE,img));
			}
			return this;
		}
		
		public DocFile addNewPage(){
			contexts.add(new DocElement(DocElement.NEWPAGE,null));
			return this;
		}
		
		public String writeFile(String filePath){
			if("rtf".equals(docType)){
				return writeRTF(filePath);
			}
			return writePDF(filePath);
		}
		/**
		 * 写PDF文件
		 * @param filePath 文件路径
		 * @return 如果写入正确，返回文件的路径���д����ȷ�������ļ���·��
		 */
		private String writePDF(String filePath){
			try{
				Document document = new Document(pageSize,marginLeft,marginRight,marginTop, marginBottom );
				PdfWriter.getInstance(document, new FileOutputStream(filePath));
				document.open();
				for(DocElement context : contexts){
					switch(context.type){
					case DocElement.PARAGRAPH:
						document.add(context.getParagraph());
						break;
					case DocElement.TABLE:
						document.add(context.getTable());
						break;
					case DocElement.IMAGE:
						document.add(context.getImage());
						break;
					case DocElement.NEWPAGE:
						document.newPage();
					}
				}
				document.close();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			return filePath;
		}
		
		
		/**
		 * 写RTF文件
		 * @param filePath 文件路径
		 * @return 如果写入正确，返回文件的路径���д����ȷ�������ļ���·��
		 */
		private String writeRTF(String filePath){
			try{
				Document document = new Document(pageSize,marginLeft,marginRight,marginTop, marginBottom );
				RtfWriter2.getInstance(document, new FileOutputStream(filePath));
				document.open();
				for(DocElement context : contexts){
					switch(context.type){
					case DocElement.PARAGRAPH:
						document.add(context.getParagraph());
						break;
					case DocElement.TABLE:
						document.add(context.getTable());
						break;
					case DocElement.IMAGE:
						document.add(context.getImage());
						break;
					case DocElement.NEWPAGE:
						document.newPage();
					}
				}
				document.close();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			return filePath;
		}
		
	}
	
	/**
	 * 内部类，用来记录写入文档的类型和数据
	 * @author 曹新龙������
	 *
	 */
	private class DocElement{
		public static final int STRING = 0;
		public static final int PARAGRAPH = 1;
		public static final int TABLE = 2;
		public static final int IMAGE = 3;
		public static final int NEWPAGE = 4;
		
		private int type;
		private Object value;
		
		public DocElement(int type, Object value) {
			this.type = type;
			this.value = value;
		}
		@SuppressWarnings("unused")
		public int getType() {
			return type;
		}
		public Object getValue() {
			return value;
		}
		
		public Paragraph getParagraph(){
			return (Paragraph)getValue();
		}
		
		public Table getTable(){
			return (Table)getValue();
		}
		
		public Image getImage(){
			return (Image)getValue();
		}
	}

	/**
	 * 表格列，一个列中可能含有多个值
	 * @author 曹新龙������
	 *
	 */
	private class TableGrid{
		private String head;
		private List<TableElement> elements= new ArrayList<TableElement>();
		
		public TableGrid(String head) {
			this.head = head;
		}
		@SuppressWarnings("unused")
		public TableGrid(String head, TableElement element) {
			this.head = head;
			addElement(element);
		}
		
		@SuppressWarnings("unused")
		public String getHead() {
			return head;
		}
		
		@SuppressWarnings("unused")
		public void setHead(String head) {
			this.head = head;
		}
		@SuppressWarnings("unused")
		public List<TableElement> getElements() {
			return elements;
		}
		@SuppressWarnings("unused")
		public void setElements(List<TableElement> elements) {
			this.elements = elements;
		}
		public TableGrid addElement(TableElement element){
			this.elements.add(element);
			return this;
		}
	}
	/**
	 * 表格的内容
	 * @author 曹新龙������
	 *
	 */
	private class TableElement{
		private TextStyle style;
		private int type;//1=text,2=image
		private String value;
		
		public TableElement(int type, String value, TextStyle style) {
			this.style = style;
			this.type = type;
			this.value = value;
		}
		
		public TextStyle getStyle() {
			return style;
		}
		@SuppressWarnings("unused")
		public void setStyle(TextStyle style) {
			this.style = style;
		}
		public int getType() {
			return type;
		}
		@SuppressWarnings("unused")
		public void setType(int type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		@SuppressWarnings("unused")
		public void setValue(String value) {
			this.value = value;
		}

		
	}
	
	/**
	 * 记录文本的类型
	 * 
	 * fontType: 表格主体文字字体
	 * fontSize: 表格主体文字大小
	 * fontStyle: 表格主体文字类型
	 * align: 表格主体对齐方式
	 * spaceLine: 段前空行
	 * firstIndent: 首行缩紧
	 * width: 宽
	 * height: 高
	 * basePath： 图片的基本路径
	 */
	public class TextStyle{
		
		private BaseFont fontType ;//文字字体
		private Integer fontSize;//文字大小
		private Integer fontStyle;//文字类型
		private Integer align;//对齐方式
		private Integer spaceLine; //段前空行
		private Integer firstIndent; //首行缩紧
		private Integer width; //宽
		private Integer height; //高
		private String basePath; //图片的基本路径
		
		
		private BaseFont fontSong;

		public TextStyle(){
			try{
				this.fontSong = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				this.fontType = fontSong;//文字字体
				this.fontSize = 12;//文字大小
				this.fontStyle = Font.NORMAL;//文字类型
				this.align = Element.ALIGN_LEFT;//对齐方式
				this.spaceLine = 0; //段前空行
				this.firstIndent = 0; //首行缩紧
				this.width=0; //宽
				this.height=0; //高
				this.basePath = "";//图片的基本路径
			}
		}
		
		public BaseFont getFontType() {
			return fontType;
		}
		public TextStyle setFontType(BaseFont fontType) {
			this.fontType = fontType;
			return this;
		}

		public TextStyle setFontType(String fontPath) {
			try {
				this.fontType = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return this;
		}
		
		public Integer getFontSize() {
			return fontSize;
		}
		public TextStyle setFontSize(Integer fontSize) {
			this.fontSize = fontSize;
			return this;
		}
		public Integer getFontStyle() {
			return fontStyle;
		}
		public TextStyle setFontStyle(Integer fontStyle) {
			this.fontStyle = fontStyle;
			return this;
		}
		public Integer getAlign() {
			return align;
		}
		public TextStyle setAlign(Integer align) {
			this.align = align;
			return this;
		}
		public Integer getSpaceLine() {
			return spaceLine;
		}
		public TextStyle setSpaceLine(Integer spaceLine) {
			this.spaceLine = spaceLine;
			return this;
		}
		public Integer getFirstIndent() {
			return firstIndent;
		}
		public TextStyle setFirstIndent(Integer firstIndent) {
			this.firstIndent = firstIndent;
			return this;
		}
		public Integer getWidth() {
			return width;
		}
		public TextStyle setWidth(Integer width) {
			this.width = width;
			return this;
		}
		public Integer getHeight() {
			return height;
		}
		public TextStyle setHeight(Integer height) {
			this.height = height;
			return this;
		}

		public String getBasePath() {
			return basePath;
		}

		public TextStyle setBasePath(String basePath) {
			this.basePath = basePath;
			return this;
		}
		

	}

	/**
	 * 记录表格的类型
	 * 
	 * border: 表格线宽度
	 * padding: 表格文字距表格线距离
	 * boderColor： 表格线颜色
	 * spacing： 表格线间距
	 * split： 分割字符
	 */
	
	public class TableStyle{
		
		private Integer borderWidth;//表格线宽度
		private Integer padding;//表格文字距表格线距离
		private Color boderColor;//表格线颜色
		private Integer spacing;//表格线间距
		private String split;//分割字符
		
		public TableStyle(){
			this.borderWidth = 1;//表格线宽度
			this.padding = 5;//表格文字距表格线距离
			this.boderColor = new Color(0,0,0);//表格线颜色
			this.spacing = 0;//
			this.split = " ";
		}
		public Integer getBorderWidth() {
			return borderWidth;
		}
		public TableStyle setBorderWidth(Integer borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}
		public Integer getPadding() {
			return padding;
		}
		public TableStyle setPadding(Integer padding) {
			this.padding = padding;
			return this;
		}
		public Color getBoderColor() {
			return boderColor;
		}
		public TableStyle setBoderColor(Color boderColor) {
			this.boderColor = boderColor;
			return this;
		}
		public Integer getSpacing() {
			return spacing;
		}
		public TableStyle setSpacing(Integer spacing) {
			this.spacing = spacing;
			return this;
		}

		public String getSplit() {
			return split;
		}

		public TableStyle setSplit(String split) {
			this.split = split;
			return this;
		}
		


	}

	public class HtmlPdfFile {

		/**
		 * 将html文件内容生成PDF文件
		 * 
		 * @param html
		 *            html文件内容，要符合规范
		 * @param pdfFileName
		 *            输出文件名，带有绝对路径
		 * @param hasChinese
		 *            是否包含中文
		 */
		public String htmlToPDF(String html, String pdfFileName,
				boolean hasChinese, boolean overwrite) throws Exception {
			String fileName = pdfFileName;
			File outputFile = new File(pdfFileName);
			if (outputFile.exists() && overwrite) {
				outputFile.delete();
			} else {
				fileName = FileUtils.getValidFileName(pdfFileName);
			}
			FileUtils.createFolder(outputFile);
			OutputStream os;
			try {
				os = new FileOutputStream(fileName);

				ITextRenderer renderer = new ITextRenderer();
				renderer.setDocumentFromString(html);
				renderer.getSharedContext().setBaseURL("file:/"+FileUtils.getROOTPath()); // 加入根路径，解决图片的读取问题
				if (hasChinese) {
					ITextFontResolver fontResolver = renderer.getFontResolver();
					try{
						String fontBasePath = FileUtils.getROOTPath();
						if (fontBasePath.endsWith("/")) {
							fontBasePath += "WEB-INF/classes/fonts/";
						} else {
							fontBasePath += "/WEB-INF/classes/fonts/";
						}
						fontResolver.addFont(fontBasePath + "simsun.ttc",
								BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
						fontResolver.addFont(fontBasePath + "simhei.ttf",
								BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 黑体字
						fontResolver.addFont(fontBasePath + "arial.ttf",
								BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 英文字体
						fontResolver.addFont(fontBasePath + "verdana.ttf",
								BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 英文字体
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				renderer.layout();
				renderer.createPDF(os);
				os.flush();
				os.close();

				// 返回生成PDF文件的路径和名字 ,以保存数据库
				return fileName;
			} catch (FileNotFoundException e) {
				// logger.error("不存在文件！" + e.getMessage());
				throw new Exception(e);
			} catch (DocumentException e) {
				// logger.error("生成pdf时出错了！" + e.getMessage());
				throw new Exception(e);
			} catch (IOException e) {
				// logger.error("pdf出错了！" + e.getMessage());
				throw new Exception(e);
			}
		}

		/**
		 * 将html片段生成PDF文件，会在html的前后加上头文件
		 * 
		 * @param htmlcode
		 *            html片段
		 * @param pdfFileName
		 *            输出文件名，带有绝对路径
		 * @param hasChinese
		 *            是否包含中文
		 */
		public String htmlCodeToPDF(String htmlcode, String pdfFileName,
				boolean hasChinese, boolean overwrite) throws Exception {
			return htmlCodeToPDF(htmlcode, null, pdfFileName, hasChinese,
					overwrite);
		}

		/**
		 * 将html片段生成PDF文件，会在html的前后加上头文件
		 * 
		 * @param htmlcode
		 *            html片段
		 * @param fontName
		 *            默认的字体
		 * @param pdfFileName
		 *            输出文件名，带有绝对路径
		 * @param hasChinese
		 *            是否包含中文
		 */
		public String htmlCodeToPDF(String htmlcode, String fontName,
				String pdfFileName, boolean hasChinese, boolean overwrite)
				throws Exception {
			if (fontName == null && hasChinese) {
				fontName = "SimSun";
			}
			String html = filterHeader(htmlcode, fontName);
			return htmlToPDF(html, pdfFileName, hasChinese, overwrite);
		}

		/**
		 * 将html文件生成pdf文件
		 * 
		 * @param htmlFileName
		 *            html文件的路径
		 * @param pdfFileName
		 *            输出文件名，带有绝对路径
		 * @param hasChinese
		 *            是否包含中文
		 */
		public String htmlFileToPDF(String htmlFileName, String pdfFileName,
				boolean hasChinese, boolean overwrite) throws Exception {
			File f = new File(htmlFileName);
			if (!f.exists()) {
				throw new Exception("File not exists. " + htmlFileName);
			}
			FileInputStream ism = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(ism);
			BufferedReader bs = new BufferedReader(isr);
			StringBuilder builder = new StringBuilder();
			String info = bs.readLine();
			while (info != null) {
				builder.append(info);
				info = bs.readLine();
			}

			return htmlToPDF(builder.toString(), pdfFileName, hasChinese,
					overwrite);
		}

		/**
		 * 将html路径生成pdf文件
		 * 
		 * @param htmlURL
		 *            html文件的地址
		 * @param parameters
		 *            通过post传输的数据
		 * @param pdfFileName
		 *            输出文件名，带有绝对路径
		 * @param hasChinese
		 *            是否包含中文
		 */
		public String htmlURLToPDF(String htmlURL,
				Map<String, String> parameters, String pdfFileName,
				boolean hasChinese, boolean overwrite) throws Exception {
			URL url;
			try {
				if (htmlURL.indexOf("?") != -1) {
					htmlURL = htmlURL + "&locale="
							+ LocaleContextHolder.getLocale().toString();
				} else {
					htmlURL = htmlURL + "?locale="
							+ LocaleContextHolder.getLocale().toString();
				}
				url = new URL(htmlURL);
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				urlConn.setDoOutput(true);
				urlConn.setDoInput(true);
				urlConn.setUseCaches(false);
				urlConn.setRequestMethod("POST");
				urlConn.connect();

				// 写入参数
				OutputStream os = urlConn.getOutputStream();
				StringBuilder param = new StringBuilder();
				for (String p : parameters.keySet()) {
					param.append("&").append(p).append("=")
							.append(parameters.get(p));
				}
				os.write(param.toString().getBytes());

				// 获得内容
				InputStream is = urlConn.getInputStream();
				Tidy tidy = new Tidy();
				OutputStream os2 = new ByteArrayOutputStream();
				tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
				tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
				tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
				tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
				tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
				tidy.parse(is, os2);

				is.close();

				// 解决乱码 --将转换后的输出流重新读取改变编码
				String temp;
				StringBuffer sb = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new ByteArrayInputStream(
								((ByteArrayOutputStream) os2).toByteArray()),
						"UTF-8"));
				while ((temp = in.readLine()) != null) {
					sb.append(temp);
				}

				// 写入pdf文件
				return htmlToPDF(sb.toString(), pdfFileName, hasChinese,
						overwrite);

			} catch (IOException e) {
				throw new Exception("读取客户端网页文本信息时出错了", e);
			}
		}

		private String filterHeader(String html, String fontName) {
			// SimSun
			return filterHeader(html, "utf-8", fontName,
					"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
		}

		/**
		 * 文件头
		 */
		private String filterHeader(String html, String encoding,
				String fontName, String htmlDTDURL) {
			StringBuilder builder = new StringBuilder();
			builder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"  \"").append(htmlDTDURL).append("\" >\n");
//			builder.append("<?xml version=\"1.0\" encoding=\"")
//					.append(encoding).append("\" ?>\n");
			builder.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
			builder.append("<head>\n");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
			builder.append("<title>PDF</title>\n");
			if (!StringUtils.isBlank(fontName)) {
				builder.append("<style type=\"text/css\">body {font-family: ")
						.append(fontName).append(";}</style>");
			}
			builder.append("</head>\n");
			builder.append("<body>");
			builder.append(html);
			builder.append("</body>\n");
			builder.append("</html>\n");
			return builder.toString();
		}

	}
}
