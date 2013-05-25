doctools
========

Tools for writing a book based on Markdown.

Usage
--------


Plugins
--------

Plugins below is available in your Markdown document.

###anchor

Define the anchor to reference from link plugin.

```
{{anchor label, title}}
```

- label: the label to reference from link plugin
- title: the title

###caption

Add the caption to figures, tables or lists.

```
{{caption category, title[, label]}}
```

- category: the numbering category such as Figure, Table or List
- title: the caption
- label: the label to reference from link plugin (optional)

###link

Reference the anchor or caption.

```
{{link [page, ]label}}
```

- page: the path of the reference page (optional)
- label: the label to reference

###keyword

Define keywords of the topic.

```
{{keyword keyword[, keyword[, keyword...]]}}
```

- keyword: arbitrary keywords

###version

Define available versions of the topic.

```
{{version version[, version[, version...]]}}
```

- version: arbitrary versions

###box

Display contents in the box.

```
{{box title
content
}}
```

- title: the title of the box
- content: the content of the box

###column

Write the column.

```
{{column title
content
}}
```

- title: the title of the column
- content: the content of the column

###note

Write the note.

```
{{note
content
}}
```

- content: the content of the note

###code

Display source code in the &lt;pre&gt;. It is highlighted by google-code-prettify.

```
{{code
content
}}
```

- content: the source code