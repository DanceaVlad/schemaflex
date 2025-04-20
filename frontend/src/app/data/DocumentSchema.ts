import { JsonSchema, UISchemaElement } from '@jsonforms/core';

export interface DocumentSchema {
    id: number;
    name: string;
    dataSchema: JsonSchema;
    uiSchema: UISchemaElement;
}
