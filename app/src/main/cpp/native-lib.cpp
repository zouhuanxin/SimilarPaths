#include <jni.h>
#include <string>
#include <stdio.h>
#include <math.h>

#define MAXLEN 100

int lena=MAXLEN,lenb=MAXLEN;
typedef struct Cordinte{
    double x;
    double y;
} Cordinte;

typedef struct {
    struct Cordinte cs[MAXLEN];
} Cors;

double count(Cors *cors1,Cors *cors2,int i,int j){
    double x = cors1->cs[i].x - cors2->cs[j].x;
    double y = cors1->cs[i].y - cors2->cs[j].y;
    if (x < 0){
        x = x * -1;
    }
    if (y < 0){
        y = y * -1;
    }
    x = x*x;
    y = y*y;
    double dis = sqrt(x+y);
    return dis;
}

double dtw(Cors *cors1,Cors *cors2,int i,int j){
    if (i >= lena || j >= lenb) {
        printf("end\n");
        return 0;
    }
    double temp1 = count(cors1, cors2,i,j);
    //f(i,j+1)
    double a = count(cors1, cors2,i,j+1);
    //f(i+1,j)
    double b = count(cors1, cors2,i+1,j);
    //f(i+1,j+1)
    double c = count(cors1, cors2,i+1,j+1);
    double temp2 = a;
    if(temp2 > b){
        temp2 = b;
    }
    if (temp2 > c) {
        temp2 = c;
    }
    if (temp2 == a) {
        printf("执行a\n");
        return temp1 + dtw(cors1, cors2,i,j+1);
    }else if(temp2 == b){
        printf("执行b\n");
        return temp1 + dtw(cors1, cors2,i+1,j);
    }else{
        printf("执行c\n");
        return temp1 + dtw(cors1, cors2,i+1,j+1);
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_similarpaths_MainActivity_init(JNIEnv *env, jobject thiz, jdoubleArray x1,
                                                jdoubleArray y1, jdoubleArray x2, jdoubleArray y2) {
    lena = env->GetArrayLength(x1);
    lenb = env->GetArrayLength(x2);
    Cors *cors1 = (Cors*)malloc(sizeof(Cors));
    Cors *cors2 = (Cors*)malloc(sizeof(Cors));
    cors1->cs[lena];
    cors2->cs[lenb];
    double *xx1 = new double[lena];
    env->GetDoubleArrayRegion(x1, 0, lena, xx1);
    double *yy1 = new double[lena];
    env->GetDoubleArrayRegion(y1, 0, lena, yy1);
    double *xx2 = new double[lenb];
    env->GetDoubleArrayRegion(x2, 0, lenb, xx2);
    double *yy2 = new double[lenb];
    env->GetDoubleArrayRegion(y2, 0, lenb, yy2);
    for (int i = 0; i < lena; ++i) {
        Cordinte cordinte;
        cordinte.x = xx1[i];
        cordinte.y = yy1[i];
        cors1->cs[i] = cordinte;
    }
    for (int i=0; i<lenb; i++) {
        Cordinte cordinte;
        cordinte.x = xx1[i];
        cordinte.y = yy2[i];
        cors2->cs[i] = cordinte;
    }
    jsize res = dtw(cors1,cors2,0,0);
    return res;
}