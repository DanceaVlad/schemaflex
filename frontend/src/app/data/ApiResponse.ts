export interface ErrorBody {
    code: string;
    message: string;
}

export interface ApiResponse<T = any> {
    success: boolean;
    errors: ErrorBody[] | null;
    data: T;
}
