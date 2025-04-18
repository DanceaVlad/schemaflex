export interface DocumentSchema {
    id: number;
    name: string;
    dataSchema: Record<string, any>;
    uiSchema: Record<string, any>;
}
