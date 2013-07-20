doctools
========

Tools for writing a book based on Markdown.

Usage
--------

 1. Clone this repository
 2. Put your *.md files into doc/
 3. sbt run

Plugins
--------

Plugins below is available in your Markdown document.

###anchor

Define the anchor to reference from link plugin.

```
{{anchor title, label}}
```

- title: the title
- label: the label to reference from link plugin

###link

Reference the anchor or caption.

```
{{link [page, ]label}}
```

- page: the relative path of the reference page (optional)
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

###code

Display source code with the caption.

```
{{code title[, label]
source code
}}
```

- title: the title of the caption
- label: the label to reference (optional)
- source code: the source code

###table

Display the table from CSV with the caption.

```
{{table title[, label]
csv
}}
```

- title: the title of the caption
- label: the label to reference (optional)
- csv: the table content (first row is header)

###figure

Display the image with the caption.

```
{{figure title[, label]
filename
}}
```
- title: the title of the caption
- label: the label to reference (optional)
- filename: the filename (or path) of the image

###memo

Write a memo for a reminder. Memo is listed at the side bar.

```
{{memo message}}
or
{{memo [name,] message}}
```

- name: the user name. It's used to identify whose memo. (optional)
- message: the message

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
{{note title
content
}}
```

- content: the content of the note
