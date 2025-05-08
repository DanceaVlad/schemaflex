export interface Document {
    name: string;
    id: number;
    schemaId: number;
    data: any;
}

export interface CreateDocumentRequest {
    name: string;
    schemaId: number;
    data: any;
}

export interface UpdateDocumentRequest {
    id: number;
    data: any;
}
