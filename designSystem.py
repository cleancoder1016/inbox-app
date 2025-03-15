import graphviz

# Create a Graphviz graph
dot = graphviz.Digraph(format='png', engine='dot')

# Set some attributes to make the diagram visually appealing
dot.attr(dpi='300', bgcolor='white', fontname='Helvetica')

# Define main components (with thicker colors)
dot.node('User', 'User', shape='ellipse', style='filled', fillcolor='lightblue', fontcolor='black', width='2.5')
dot.node('Frontend', 'Frontend (Thymeleaf)', shape='rect', style='filled', fillcolor='lightgreen', fontcolor='black', width='2.5')
dot.node('Backend', 'Backend (SpringBoot)', shape='rect', style='filled', fillcolor='lightyellow', fontcolor='black', width='2.5')
dot.node('Database', 'Database (Cassandra)', shape='cylinder', style='filled', fillcolor='lightcoral', fontcolor='black', width='2.5')

# Define sub-components of Backend (with different shapes for clarity)
dot.node('Authentication', 'Authentication/Authorization', shape='rect', style='filled', fillcolor='lightgrey', fontcolor='black')
dot.node('FolderRetrieval', 'Folder Retrieval', shape='rect', style='filled', fillcolor='lightgrey', fontcolor='black')
dot.node('MessageRetrieval', 'Message Retrieval', shape='rect', style='filled', fillcolor='lightgrey', fontcolor='black')
dot.node('MessageSending', 'Message Sending', shape='rect', style='filled', fillcolor='lightgrey', fontcolor='black')
dot.node('ReplyHandling', 'Reply Handling', shape='rect', style='filled', fillcolor='lightgrey', fontcolor='black')

# Database tables inside the Database container
dot.node('UsersTable', 'users', shape='rect', style='filled', fillcolor='beige', fontcolor='black')
dot.node('FoldersTable', 'folders_by_user', shape='rect', style='filled', fillcolor='beige', fontcolor='black')
dot.node('MessagesTable', 'messages_by_user_folder', shape='rect', style='filled', fillcolor='beige', fontcolor='black')
dot.node('MessageByIdTable', 'message_by_id', shape='rect', style='filled', fillcolor='beige', fontcolor='black')
dot.node('UnreadEmailStatsTable', 'unread_email_stats', shape='rect', style='filled', fillcolor='beige', fontcolor='black')
dot.node('ThreadsTable', 'message_threads', shape='rect', style='filled', fillcolor='beige', fontcolor='black')

# Add connections (arrows) to represent the flow between components
dot.edge('User', 'Frontend', label='User Interaction')
dot.edge('Frontend', 'Backend', label='HTTP Requests')
dot.edge('Backend', 'Frontend', label='HTTP Responses')

# Connect Backend processes to the Database (Cassandra) tables
dot.edge('Backend', 'Authentication', label='Authentication/Authorization')
dot.edge('Backend', 'FolderRetrieval', label='Folder Retrieval')
dot.edge('Backend', 'MessageRetrieval', label='Message Retrieval')
dot.edge('Backend', 'MessageSending', label='Message Sending')
dot.edge('Backend', 'ReplyHandling', label='Reply Handling')

dot.edge('Authentication', 'UsersTable', label='Users')
dot.edge('FolderRetrieval', 'FoldersTable', label='Folders by User')
dot.edge('FolderRetrieval', 'UnreadEmailStatsTable', label='Unread Email Stats')
dot.edge('MessageRetrieval', 'MessagesTable', label='Messages by User Folder')
dot.edge('MessageRetrieval', 'MessageByIdTable', label='Message by ID')
dot.edge('MessageSending', 'MessagesTable', label='Messages by User Folder')
dot.edge('MessageSending', 'MessageByIdTable', label='Message by ID')
dot.edge('MessageSending', 'UnreadEmailStatsTable', label='Unread Email Stats')
dot.edge('ReplyHandling', 'ThreadsTable', label='Message Threads')
dot.edge('ReplyHandling', 'MessagesTable', label='Messages by User Folder')
dot.edge('ReplyHandling', 'UnreadEmailStatsTable', label='Unread Email Stats')

# Save the diagram to a file
output_file = 'inboxapp_system_design_simple'
dot.render(output_file)
