/*
 * This file is part of trolCommander, http://www.trolsoft.ru/en/soft/trolcommander
 * Copyright (C) 2013-2014 Oleg Trifonov
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mucommander.ui.main.quicklist;

import com.mucommander.commons.file.AbstractFile;
import com.mucommander.commons.file.DummyFile;
import com.mucommander.commons.file.FileFactory;
import com.mucommander.utils.text.Translator;
import com.mucommander.ui.action.ActionProperties;
import com.mucommander.ui.action.MuAction;
import com.mucommander.ui.action.impl.ShowRecentViewedFilesQLAction;
import com.mucommander.ui.action.impl.ViewAction;
import com.mucommander.ui.main.FolderPanel;
import com.mucommander.ui.main.MainFrame;
import com.mucommander.ui.quicklist.QuickListWithIcons;
import com.mucommander.ui.viewer.ViewerRegistrar;
import com.mucommander.ui.viewer.text.TextFilesHistory;

import javax.swing.Icon;
import java.util.List;

/**
 * @author Oleg Trifonv
 * Created on 01/07/14.
 */
public class RecentViewedQL extends QuickListWithIcons<AbstractFile> {

    private static final int MAX_FILES_IN_LIST = 50;

    private final MainFrame mainFrame;

    public RecentViewedQL(FolderPanel folderPanel) {
        super(folderPanel, ActionProperties.getActionLabel(ShowRecentViewedFilesQLAction.Descriptor.ACTION_ID), Translator.get("recent_viewed_files_quick_list.empty_message"));
        this.mainFrame = folderPanel.getMainFrame();
    }

    @Override
    protected Icon itemToIcon(AbstractFile item) {
        return MuAction.getStandardIcon(ViewAction.class);
    }

    @Override
    protected AbstractFile[] getData() {
        List<AbstractFile> list = TextFilesHistory.getInstance().getLastList(MAX_FILES_IN_LIST);
        return list.toArray(new AbstractFile[list.size()]);
    }

    @Override
    protected void acceptListItem(AbstractFile item) {
        if (item instanceof DummyFile) {
            item = FileFactory.getFile(item.getURL());
        }
        if (item.exists()) {
            ViewerRegistrar.createViewerFrame(mainFrame, item, ActionProperties.getActionIcon(ViewAction.Descriptor.ACTION_ID).getImage());
        } else {
            // TODO error message
        }
    }
}
