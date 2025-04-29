export interface Document {
    id: number;
    schemaId: number;
    data: any;
}

export interface CreateDocumentRequest {
    schemaId: number;
    data: any;
}

export interface UpdateDocumentRequest {
    id: number;
    data: any;
}
