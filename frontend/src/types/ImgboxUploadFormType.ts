export type ImgboxUploadFormType = {
    title : string,
    description? : string,
    tags : string[],
    files : File[] | null
}