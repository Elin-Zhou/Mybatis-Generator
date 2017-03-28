/**
 * Copyright 2006-2015 the original author or authors. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * BaseColumnList xml生成类
 * 
 * @author Jeff Butler
 */
public class BaseColumnListElementGenerator extends AbstractXmlElementGenerator {

    public BaseColumnListElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                                          introspectedTable.getBaseColumnListId()));

        context.getCommentGenerator().addComment(answer);
        Iterator<IntrospectedColumn> iter = introspectedTable.getNonBLOBColumns().iterator();

        XmlElement query = new XmlElement("if");
        query.addAttribute(new Attribute("test", "querySet != null"));
        XmlElement trim = new XmlElement("trim");
        trim.addAttribute(new Attribute("suffixOverrides", ","));

        StringBuilder sb;
        XmlElement node;
        while (iter.hasNext()) {
            String columnName = MyBatis3FormattingUtilities.getSelectListPhrase(iter.next());
            node = new XmlElement("if");
            node.addAttribute(new Attribute("test", "querySet." + columnName + " != null"));
            sb = new StringBuilder();
            node.addElement(new TextElement(sb.append(columnName).append(",").toString()));
            trim.addElement(node);
        }
        query.addElement(trim);
        answer.addElement(query);

        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
