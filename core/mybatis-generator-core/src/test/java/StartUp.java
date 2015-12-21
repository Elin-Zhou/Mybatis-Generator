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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


/**
 * @author ElinZhou E-mail:zf@yumeitech.com.cn
 * @version 创建时间：2015年12月16日 下午4:54:18
 *
 */
public class StartUp {
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();
		File configFile = new File(StartUp.class.getResource("/generatorConfig.xml").toURI());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration configuration = cp.parseConfiguration(configFile);
		DefaultShellCallback shellCallback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
		myBatisGenerator.generate(null);
		System.out.println(warnings);
	}
}
