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


package de.bitexpert.eclipse.yuicompressor.wizards;


import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;


/**
 * New File Wizard
 *
 * @author	Stephan Hochdoerfer <S.Hochdoerfer@bitExpert.de>
 */


public class NewFileWizard extends Wizard implements INewWizard
{
	private IStructuredSelection selection;
	private NewFileWizardPage newFileWizardPage;
	private String choosenFilename;
	private IPath choosenPath;


	/**
	 * Creates a new {@link NewFileWizard}.
	 */
	public NewFileWizard()
	{
		setWindowTitle("Select target file");
	}


	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages()
	{
		this.newFileWizardPage = new NewFileWizardPage(selection);
		addPage(this.newFileWizardPage);
	}


	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 * @return boolean
	 */
	public boolean performFinish()
	{
		// build the complete filename relative to the workspace root directory
		this.choosenPath     = this.newFileWizardPage.getContainerFullPath();
		this.choosenFilename = this.newFileWizardPage.getFileName();
		return true;
	}


	/**
	 * Returns the filename that was choosen in the wizard page dialog.
	 *
	 * @return String
	 */
	public String getFilename()
	{
		/**
		 * since the page gets disposed after finishing the dialog we cannot
		 * access the member from the page. Thus we copy the needed information
		 * in the <code>finish</code> method to another local member.
		 */
		return this.choosenFilename;
	}


	/**
	 * Returns the container that was choosen in the wizard page dialog.
	 *
	 * @return IPath
	 */
	public IPath getPath()
	{
		/**
		 * since the page gets disposed after finishing the dialog we cannot
		 * access the member from the page. Thus we copy the needed information
		 * in the <code>finish</code> method to another local member.
		 */
		return this.choosenPath;
	}


	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		this.selection = selection;
	}
}