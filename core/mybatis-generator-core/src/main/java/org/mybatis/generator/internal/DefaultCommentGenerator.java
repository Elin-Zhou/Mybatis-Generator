/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * The Class DefaultCommentGenerator.
 *
 * @author Jeff Butler
 */
public class DefaultCommentGenerator implements CommentGenerator {

    /** The properties. */
    private Properties properties;

    /** The suppress date. */
    private boolean suppressDate;

    /** The suppress all comments. */
    private boolean suppressAllComments;

    /** The addition of table remark's comments.
     * If suppressAllComments is true, this option is ignored*/
    private boolean addRemarkComments;

    /**
     * Instantiates a new default comment generator.
     */
    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

	/**
	 * 文件顶部注释
	 */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/**");
        compilationUnit.addFileCommentLine(" * Yumeitech.com.cn Inc.");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		compilationUnit.addFileCommentLine(
				" * Copyright (c) 2012-" + simpleDateFormat.format(new Date()) + " All Rights Reserved.");
        compilationUnit.addFileCommentLine(" */");
        return;
    }

    public void addComment(XmlElement xmlElement) {
		if (suppressAllComments) {
			return;
		}
	}

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addRootComment(org.mybatis.generator.api.dom.xml.XmlElement)
     */
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addConfigurationProperties(java.util.Properties)
     */
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
	 * 生成格式化完后的日期
	 */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else {
            return new Date().toString();
        }
    }

	/**
	 * 类注释
	 */
    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		innerClass.addJavaDocLine("/**");
		innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getRemarks());
		innerClass.addJavaDocLine(" * ");
		innerClass.addJavaDocLine(" * @author MyBatis Generator 自动生成");
		innerClass.addJavaDocLine(" * @version v 0.1 " + formate.format(new Date()) + " MyBatis Generator Exp $");
		innerClass.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addTopLevelClassComment(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addEnumComment(org.mybatis.generator.api.dom.java.InnerEnum, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addEnumComment(InnerEnum innerEnum,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

	/**
	 * 变量注释
	 */
    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
			field.addJavaDocLine("/**");
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
			field.addJavaDocLine(" */");
		}
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGeneralMethodComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

	/**
	 * Get方法注释
	 */
    public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 获得" + introspectedColumn.getRemarks());
		method.addJavaDocLine(" */");
    }

	/**
	 * Set方法注释
	 */
    public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 设置" + introspectedColumn.getRemarks());
		method.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable, boolean)
     */
    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

	/**
	 * 方法注释
	 */
	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable, String comments) {
		if (suppressAllComments) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		String remark = comments;
		if (remark != null && remark.length() != 0) {
			method.addJavaDocLine("/**"); //$NON-NLS-1$
			sb.append(" * "); //$NON-NLS-1$
			remark = remark.replaceAll("\n", "<br>\n\t * ");
			sb.append(remark);
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" */"); //$NON-NLS-1$
		}
	}
}
