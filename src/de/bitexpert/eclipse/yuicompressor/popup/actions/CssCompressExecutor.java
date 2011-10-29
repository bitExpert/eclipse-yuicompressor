/*
 * Copyright (c) 2007-2011 bitExpert AG
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
import com.yahoo.platform.yui.compressor.CssCompressor;


/**
 * Action to execute the YUI CSS Compressor.
 *
 * @author	Stephan Hochdoerfer <S.Hochdoerfer@bitExpert.de>
 */


public class CssCompressExecutor extends ExecAction
{
	/**
	 * Creates a new {@link CssCompressExecutor}.
	 *
	 * @throws IOException
	 */
	public CssCompressExecutor() throws IOException
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
		
		// execute css compression
		CssCompressor compressor = new CssCompressor(reader);
		compressor.compress(
			writer,
			linebreakpos
		);
	}
}