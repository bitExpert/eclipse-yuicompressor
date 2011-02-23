/*
 * Copyright (c) 2007-2010 bitExpert AG
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


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import com.yahoo.platform.yui.compressor.CssCompressor;
import de.bitexpert.eclipse.yuicompressor.wizards.NewFileWizard;


/**
 * Action to execute the YUI CSS Compressor.
 *
 * @author	Stephan Hochdoerfer <S.Hochdoerfer@bitExpert.de>
 */


public class CssCompressExecutor implements IObjectActionDelegate
{
	private IStructuredSelection structuredSelection;


	/**
	 * Creates a new {@link CssCompressExecutor}.
	 *
	 * @throws IOException
	 */
	public CssCompressExecutor() throws IOException
	{
		super();
	}


	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart)
	{
	}


	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action)
	{
		if(null == this.structuredSelection)
		{
			return;
		}

		IFile selectedFile = (IFile) this.structuredSelection.getFirstElement();
		if(null != selectedFile)
		{
			try
			{
				// Instantiates and initializes the wizard
				NewFileWizard wizard = new NewFileWizard();
				wizard.init(
					PlatformUI.getWorkbench(),
					this.structuredSelection
				);

				// Instantiates wizard container with the wizard and opens it
				WizardDialog dialog = new WizardDialog(new Shell(), wizard);
				dialog.setBlockOnOpen(true);
				dialog.create();
				if(dialog.open() == Window.OK)
				{
					int linebreakpos = -1;
					InputStream in   = selectedFile.getContents();
					String charset   = selectedFile.getCharset();
					FileOutputStream out = new FileOutputStream(selectedFile.
						getWorkspace().getRoot().getLocation().
						append(wizard.getFilename()).toOSString());
					InputStreamReader reader  = new InputStreamReader(in);
					OutputStreamWriter writer = new OutputStreamWriter(
						out,
						charset
					);

					// compress the file
					CssCompressor compressor = new CssCompressor(reader);
					compressor.compress(writer, linebreakpos);

					// close input/output streams
					writer.close();
					reader.close();

					// refresh workspace project to see the newly created file
					selectedFile.getProject().refreshLocal(
						IResource.DEPTH_INFINITE,
						null
					);
				}
			}
			catch(CoreException e)
			{
				MessageDialog.openInformation(null, "YUI Compressor",
					"Undefined error!");
			}
			catch(IOException e)
			{
				MessageDialog.openInformation(null, "YUI Compresssor",
					"Read/write error!");
			}
			catch(Exception e)
			{
			}
		}
	}


	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
		if (selection instanceof IStructuredSelection)
		{
			structuredSelection = (IStructuredSelection) selection;
		}
	}
}