
export class Category {
  categoryId!: number;
  parentCategoryId!: number;
  internalId!: number;
  description!: string;
}

export class Classroom {
  classroomId!: number;
  name!: string;
  recommendedCapacity!: number;
}

export class Subject {
  subjectId!: number;
  code!: string;
  name!: string;
  numberOfCredits: number;
  required: boolean;
  subjectIndex!: number;
}
