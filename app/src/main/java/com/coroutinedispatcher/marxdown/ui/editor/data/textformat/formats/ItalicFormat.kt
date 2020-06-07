package com.coroutinedispatcher.marxdown.ui.editor.data.textformat.formats

import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.TextFormat

data class ItalicFormat(
    override var from: Int = -1,
    override var to: Int = -1
) : TextFormat