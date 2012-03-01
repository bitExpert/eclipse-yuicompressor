/*
 * Copyright (c) 2007-2012 bitExpert AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package de.bitexpert.eclipse.yuicompressor.popup.actions;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.mozilla.javascript.EvaluatorException;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;


/**
 * Action which executes the YUI Javascript Compressor.
 *
 * @author	Stephan Hochdoerfer <S.Hochdoerfer@bitExpert.de>
 */


public class JsCompressExecutor extends ExecAction
{
	/**
	 * Creates a new {@link JsCompressExecutor}.
	 *
	 * @throws IOException
	 */
	public JsCompressExecutor() throws IOException
	{
		super();
	}


	/*
	 * (non-Javadoc)
	 * @see de.bitexpert.eclipse.yuicompressor.popup.actions.ExecAction#exec(java.io.InputStreamReader, java.io.OutputStreamWriter)
	 */
	protected void exec(InputStreamReader reader, OutputStreamWriter writer) throws EvaluatorException, IOException
	{
		// global configuration options. Could be set per preference page later
		// on!
		int linebreakpos = -1;
		boolean verbose  = false;
		boolean munge    = false;
		boolean preserveAllSemiColons = true;
		boolean disableOptimizations  = true;
		
		// execute javascript compression
		JavaScriptCompressor compressor = new JavaScriptCompressor(
			reader,
			null
		);
		
		compressor.compress(
			writer,
			linebreakpos,
			munge,
			verbose,
			preserveAllSemiColons,
			disableOptimizations
		);
	}
}