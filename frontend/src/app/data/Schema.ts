import { JsonSchema, UISchemaElement } from '@jsonforms/core';

export interface Schema {
    id: number;
    name: string;
    dataSchema: JsonSchema;
    uiSchema: UISchemaElement;
}
