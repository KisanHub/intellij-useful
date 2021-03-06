/*
 * This file is part of intellij-useful. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-useful/master/COPYRIGHT. No part of intellij-orogeny, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2015 The developers of intellij-useful. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-useful/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.inspection.inspectionToolPresentationFactories;

import com.intellij.codeInspection.ex.InspectionToolWrapper;
import com.intellij.codeInspection.ui.InspectionToolPresentation;
import com.kisanhub.intellij.useful.inspection.ProblemRefElementHandler;
import com.kisanhub.intellij.useful.inspection.UsefulGlobalInspectionContextImpl;
import com.kisanhub.intellij.useful.inspection.inspectionToolPresentations.UsefulInspectionToolPresentation;
import org.jetbrains.annotations.NotNull;

public final class UsefulInspectionToolPresentationFactory implements InspectionToolPresentationFactory
{
	@NotNull
	private final ProblemRefElementHandler problemRefElementHandler;

	public UsefulInspectionToolPresentationFactory(@NotNull final ProblemRefElementHandler problemRefElementHandler)
	{
		this.problemRefElementHandler = problemRefElementHandler;
	}

	@NotNull
	@Override
	public InspectionToolPresentation inspectionToolPresentation(@NotNull final UsefulGlobalInspectionContextImpl usefulGlobalInspectionContext, @NotNull final InspectionToolWrapper<?, ?> inspectionToolWrapper)
	{
		return new UsefulInspectionToolPresentation(usefulGlobalInspectionContext, inspectionToolWrapper, problemRefElementHandler);
	}
}
