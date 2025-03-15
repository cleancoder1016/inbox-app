# Functional Requirements
- compose message
- send message
- view my message
- folder/label - inbox
                  sent
                   (user folder)
- reply, reply all
- view a single message



# Non-functional requirements
- Highly avaliable
- highly scalable
- Authentication
- Authorization

# Tech Stack
- SpringBoot for backend
- ThymeLeaf for frontend
- Spring Security for security
- Apache Cassandra for storage

# InboxApp Schema Design

## Keyspace Creation
```cql
CREATE KEYSPACE inbox_app WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};
```

## Use Keyspace
```cql
USE inbox_app;
```

## User Table (Authentication and Authorization)
```cql
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    username TEXT,
    password TEXT,
    email TEXT,
    created_at TIMESTAMP
);
```

## Folders by User (Inbox, Sent, Custom Folders)
```cql
CREATE TABLE folders_by_user (
    user_id UUID,
    folder_name TEXT,
    created_at TIMESTAMP,
    PRIMARY KEY (user_id, folder_name)
);
```

## Messages by User Folder (Denormalized for Fast Access)
```cql
CREATE TABLE messages_by_user_folder (
    user_id UUID,
    folder_name TEXT,
    message_id UUID,
    sender_id UUID,
    recipient_ids LIST<UUID>,
    subject TEXT,
    body TEXT,
    created_at TIMESTAMP,
    is_read BOOLEAN,
    PRIMARY KEY ((user_id, folder_name), created_at, message_id)
) WITH CLUSTERING ORDER BY (created_at DESC);
```

## Message by ID (For Viewing a Single Message)
```cql
CREATE TABLE message_by_id (
    message_id UUID PRIMARY KEY,
    sender_id UUID,
    recipient_ids LIST<UUID>,
    subject TEXT,
    body TEXT,
    created_at TIMESTAMP,
    is_read BOOLEAN
);
```

## Unread Email Stats (Managing Unread Message Counts)
```cql
CREATE TABLE unread_email_stats (
    user_id UUID,
    folder_name TEXT,
    unread_count COUNTER,
    PRIMARY KEY (user_id, folder_name)
);
```

## Thread Management (Reply and Reply All)
```cql
CREATE TABLE message_threads (
    thread_id UUID,
    message_id UUID,
    user_id UUID,
    subject TEXT,
    body TEXT,
    parent_message_id UUID,
    created_at TIMESTAMP,
    PRIMARY KEY (thread_id, created_at, message_id)
) WITH CLUSTERING ORDER BY (created_at DESC);
```
